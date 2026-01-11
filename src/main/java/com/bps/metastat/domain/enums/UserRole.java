package com.bps.metastat.domain.enums;

public enum UserRole {
  ADMIN, // Full access: CRUD all data + Approve/Publish
  USER   // Limited: Create/Edit own drafts only
}
