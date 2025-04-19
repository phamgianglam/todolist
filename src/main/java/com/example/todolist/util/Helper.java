package com.example.todolist.util;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public final class Helper {

  @Value("${jwt.enabled}")
  private boolean isSecurityEnabled;

  private Helper() {}

  // Method to convert ZonedDateTime to ISO string
  public String convertDateTimeObjectToIsoString(ZonedDateTime dateTime) {
    if (dateTime == null) {
      throw new IllegalArgumentException("ZoneDateTime cannot be null");
    }
    return dateTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
  }

  // Method to check if the ISO string has timezone
  public boolean hasTimeZone(String isostirng) {
    if (isostirng == null) {
      return false;
    }
    return isostirng.matches(".*(Z|[+-]\\d{2}:\\d{2})$");
  }

  // Method to convert ISO string to ZonedDateTime
  public ZonedDateTime convertIsoStringToZoneDateTime(String isostring) {
    String timezoneString = hasTimeZone(isostring) ? isostring : isostring + "Z";
    return ZonedDateTime.parse(timezoneString, DateTimeFormatter.ISO_ZONED_DATE_TIME);
  }

  // Instance method to check if user is admin
  public boolean isAdmin() {
    if (isSecurityEnabled) { // Use instance variable
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      if (auth == null) {
        return false;
      }

      Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
      return authorities.stream()
          .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
    } else {
      return true; // If security is not enabled, assume admin access
    }
  }

  // Method to get the current username
  public String getCurrentUserName() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return auth != null ? auth.getName() : null;
  }
}
