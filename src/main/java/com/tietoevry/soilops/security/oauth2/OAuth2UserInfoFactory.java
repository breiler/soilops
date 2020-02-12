package com.tietoevry.soilops.security.oauth2;

import com.tietoevry.soilops.security.oauth2.provider.AuthProviderEnum;
import com.tietoevry.soilops.security.oauth2.provider.FacebookOAuth2UserInfo;
import com.tietoevry.soilops.security.oauth2.provider.GithubOAuth2UserInfo;
import com.tietoevry.soilops.security.oauth2.provider.GoogleOAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(AuthProviderEnum.google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthProviderEnum.facebook.toString())) {
            return new FacebookOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthProviderEnum.github.toString())) {
            return new GithubOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}