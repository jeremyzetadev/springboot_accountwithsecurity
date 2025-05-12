package com.vikingbank.services;


import com.amdelamar.jotp.OTP;

import com.amdelamar.jotp.type.Type;

import com.vikingbank.controller.dto.UserRegistrationDto;

import com.vikingbank.entities.Role;

import com.vikingbank.entities.User;

import com.vikingbank.exceptions.*;

import com.vikingbank.filter.JsonUserSettingFilter;

import com.vikingbank.repositories.UserRepository;

import org.apache.commons.lang3.RandomStringUtils;

import org.bouncycastle.jcajce.BCFKSLoadStoreParameter;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;


import java.io.*;

import java.net.HttpURLConnection;

import java.net.URL;

import java.net.URLEncoder;

import java.nio.charset.StandardCharsets;

import java.nio.file.Files;

import java.nio.file.Path;

import java.nio.file.Paths;

import java.security.KeyPair;

import java.util.*;

import java.util.stream.Collectors;


import io.jsonwebtoken.impl.crypto.EllipticCurveProvider;


@Service

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Value("${upload.dir}")

    private String UPLOAD_DIRECTORY;


    @Autowired

    @Qualifier("passwordEncoder")

    private PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository) {

        super();

        this.userRepository = userRepository;

    }


    @Override

    public User save(UserRegistrationDto registrationDto) {

        User user = new User(registrationDto.getFirstName(), registrationDto.getLastName(), registrationDto.getEmail(),

                passwordEncoder.encode(registrationDto.getPassword()), List.of(new Role("ROLE_USER")));

        if (user.getSecret() == null) {

            user.setGeneratedSecret(OTP.randomBase32(20)); // create secret on the fly

        }

        return userRepository.save(user);

    }


    @Override

    public Optional<User> findOneByEmail(String email) {

        return userRepository.findOneByEmail(email);

    }


    @Override

    public List<User> findAll() {

        return userRepository.findAll();

    }


    @Override

    public User saveBaseUser(User user) {

        return userRepository.save(user);

    }


    @Override

    public User changePassword(User user, String password) {

        user.updatePassword(passwordEncoder.encode(password));

        return userRepository.save(user);

    }


    @Override

    public Optional<User> findOneById(Long id) {

        return userRepository.findOneById(id);

    }


    @Override

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        Optional<User> userOptional = userRepository.findByEmail(username);


        if (userOptional.isEmpty()) {

            throw new UserNotFoundException();

        }


        User user = userOptional.get();


        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),

                mapRolesToAuthorities(user.getRoles()));

    }


    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {

        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

    }


    @Override

    public String configure2FA(User user) throws UnsupportedEncodingException {

        String otpUrl = OTP.getURL(user.getSecret(), 6, Type.TOTP, "java-vikingbank", user.getEmail());


        return String.format(

                "https://chart.googleapis.com/chart?cht=qr&chs=200x200&chl=%s",

                URLEncoder.encode(otpUrl, StandardCharsets.UTF_8));

    }


    public boolean validate(Long id, User user) throws Exception {

        if (!id.equals(user.getId())) {

            throw new UserNotFoundException();

        }

        return true;

    }


    public boolean validatePasswordComplexity(String password) {

        return password.length() >= 10;

    }


    private boolean isMimeTypeAllowed(String filename) throws IOException {

        List<String> allowed = new ArrayList<>();

        allowed.add("image/png");

        allowed.add("image/jpg");


        String mimeType = mimeTypeFromFile(filename);


        return allowed.stream().anyMatch(mimeType.toLowerCase()::equals);

    }


    private boolean isValidFileSize(File file) {

        return (file.length() / 1024 / 1024) < 5;

    }


    public String checkUploadedImage(MultipartFile file, User user) throws Exception {

        if (!isMimeTypeAllowed(file.getOriginalFilename())) {

            throw new Exception("File extension not supported");

        }


        if (!isValidFileSize(new File(Objects.requireNonNull(file.getOriginalFilename())))) {

            throw new FileSizeTooLargeException();

        }

        String mimeType = mimeTypeFromFile(Objects.requireNonNull(file.getOriginalFilename()));

        String randomName = RandomStringUtils.randomAlphanumeric(10);

        StringBuilder uploadedFile = new StringBuilder();

        uploadedFile.append(Paths.get(UPLOAD_DIRECTORY, randomName)); // upload dir + random file name

        String extension = mimeType.substring(mimeType.length() - 3);

        uploadedFile.append(".").append(extension);//add extension

        user.updateProfilePicture((randomName + "." + extension));

        Files.write(Paths.get(uploadedFile.toString()), file.getBytes());

        saveBaseUser(user);

        return user.getProfilePicture();

    }


    private String mimeTypeFromFile(String filename) throws IOException {

        return Files.probeContentType(Path.of(Objects.requireNonNull(filename)));

    }


    public void downloadRemoteImage(User user, String url) throws Exception {

        URL urlReference = new URL(url);

        StringBuilder newFileNameBuilder = new StringBuilder();

        String randomName = RandomStringUtils.randomAlphanumeric(10);

        newFileNameBuilder.append(randomName);

        Path path = Paths.get(UPLOAD_DIRECTORY, newFileNameBuilder.toString());

        String mimeType = mimeTypeFromFile(path.toFile().getAbsolutePath());

        String extension = mimeType.substring(mimeType.length() - 3);


        if (!(url.toLowerCase().endsWith(".png") || url.toLowerCase().endsWith(".jpg"))) {

            throw new InvalidRemoteImageException();

        }


        if (!url.startsWith("https://www.gravatar.com/")) {

            throw new InvalidRemoteImageException();

        }


        try {

            HttpURLConnection connection = (HttpURLConnection) urlReference.openConnection();

            connection.setRequestMethod("GET");

            connection.connect();


            if (connection.getResponseCode() == 404) {

                throw new RemoteImageNotFoundException();

            }


            if (connection.getResponseCode() > 299 || connection.getResponseCode() < 200) {

                throw new InvalidRemoteImageException(); // any status other than 2xx

            }

        } catch (IOException e) {

            throw new RuntimeException("The supplied url was invalid");

        }



        if (!isMimeTypeAllowed(path.toString())) {

            throw new Exception("File mimetype invalid");

        }


        if (!isValidFileSize(path.toFile())) {

            throw new FileSizeTooLargeException();

        }


        try (InputStream in = urlReference.openStream()) {

            Files.copy(in, path);

        } catch (Exception e) {

            throw new RuntimeException("Could not download remote image");

        }


        String finalRandomName = randomName + "." + extension;

        Files.move(Paths.get(UPLOAD_DIRECTORY, newFileNameBuilder.toString()), Paths.get(UPLOAD_DIRECTORY, newFileNameBuilder.append("." + extension).toString()));// move to include extension.

        user.updateProfilePicture(finalRandomName);

        saveBaseUser(user);

    }


    public void importUserSettings(String cookieValue, User currentUser) throws IOException {

        byte[] bytes = Base64.getDecoder().decode(cookieValue);


        if (Jwts.parser().isSigned(new String(bytes))) {

            String parsedjwts = Jwts.parser()

                    .setSigningKey(currentUser.getPublicKey())

                    .parse(bytes);


            ByteArrayInputStream buffer = new ByteArrayInputStream(parsedjwts.getBytes());

            ObjectInputStream objectInputStreamCookie = new ObjectInputStream(buffer);


            objectInputStreamCookie.setObjectInputFilter(JsonUserSettingFilter::filter);

            try (ObjectInputStream stream = new ObjectInputStream(buffer)) {

                JSONObject jsonUserProperties = (JSONObject) stream.readObject();


                if (jsonUserProperties.has("theme")) {

                    if (jsonUserProperties.get("theme").equals("dark") || jsonUserProperties.get("theme").equals("light")) {

                        currentUser.setTheme(jsonUserProperties.getString("theme"));

                    }

                    if (jsonUserProperties.get("fontsize").equals("small") || jsonUserProperties.get("fontsize").equals("medium") | jsonUserProperties.get("fontsize").equals("large")) {

                        currentUser.setFontSize(jsonUserProperties.getString("fontsize"));

                    }

                }


            } catch (IOException | ClassNotFoundException e) {

                throw new InvalidUserSettingsException(e.getMessage(), e);

            }

            objectInputStreamCookie.close();

            buffer.close();

        } else {

            throw new InvalidUserSettingsException();

        }

    }


    public String exportUserSettings(User user) {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("theme", user.getTheme());

        jsonObject.put("fonsize", user.getFontSize());


        KeyPair keyPair = EllipticCurveProvider.generateKeyPair(BCFKSLoadStoreParameter.SignatureAlgorithm.ES256);


        user.setOnce(keyPair.getPublic().toString());

        saveBaseUser(user);

        return Base64.getEncoder().encodeToString(Jwts.builder()

                .setPayload(jsonObject.toString())

                .signWith(BCFKSLoadStoreParameter.SignatureAlgorithm.ES256, keyPair.getPrivate())

                .compact());

    }

}
