package com.example.todolist.util;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public final class Helper {
  @Value("${jwt.enabled}") private static boolean isSecurityEnabled;

  public static String convertDateTimeObjectToIsoString(ZonedDateTime dateTime) {
    if (dateTime == null) {
      throw new IllegalArgumentException("ZoneDateTime cannot be null");
    }

    return dateTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
  }

  public static boolean hasTimeZone(String isostirng) {
    if (isostirng == null) {
      return false;
    }

    return isostirng.matches(".*(Z|[+-]\\d{2}:\\d{2})$");
  }

  public static ZonedDateTime convertISoStringToZoneDateTime(String isostring) {
    String timezoneString = hasTimeZone(isostring) ? isostring : isostring + "Z";
    return ZonedDateTime.parse(timezoneString, DateTimeFormatter.ISO_ZONED_DATE_TIME);
  }

  public static boolean isAdmin() {
    if (isSecurityEnabled) {
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();

      if (auth == null) {
        return false;
      }

      Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
      return authorities.stream()
              .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
    }
    else {
      return true;
    }

  }

  public static String getCurrentUsername() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return auth != null ? auth.getName() : null;
  }
}
