package com.kyyba.poc.spring.config;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public class AppProperties {

	@Valid
	private final Centrify centrify = new Centrify();

	public Centrify getCentrify() {
		return centrify;
	}

	public class Centrify {
		@NotNull
		private String tenantUrl;
		@NotNull
		private String authUser;
		@NotNull
		private String authPassword;
		@NotNull
		private String tenantId;
		@NotNull
		private String version;

		public String getTenantUrl() {
			return tenantUrl;
		}

		public void setTenantUrl(String tenantUrl) {
			this.tenantUrl = tenantUrl;
		}

		public String getTenantId() {
			return tenantId;
		}

		public void setTenantId(String tenantId) {
			this.tenantId = tenantId;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public String getAuthUser() {
			return authUser;
		}

		public void setAuthUser(String authUser) {
			this.authUser = authUser;
		}

		public String getAuthPassword() {
			return authPassword;
		}

		public void setAuthPassword(String authPassword) {
			this.authPassword = authPassword;
		}
	}

}
