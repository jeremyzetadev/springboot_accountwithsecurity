

package com.vikingbank.filter;


import com.vikingbank.rest.objects.UserSetting;


import java.io.ObjectInputFilter;


public class JsonUserSettingFilter {

    public static ObjectInputFilter.Status filter(ObjectInputFilter.FilterInfo info) {

        Class<?> serialClass = info.serialClass();

        if (serialClass != null) {

            return serialClass.getName().equals(UserSetting.class.getName())

                    ? ObjectInputFilter.Status.ALLOWED

                    : ObjectInputFilter.Status.REJECTED;

        }

        return ObjectInputFilter.Status.UNDECIDED;

    }

}
