package nativeimage.core;

public class ReflectionConfig {
	private String name;
	private boolean allDeclaredConstructors;
	private boolean allPublicConstructors;
	private boolean allDeclaredMethods;
	private boolean allPublicMethods;
	private boolean allPublicFields;
	private boolean allDeclaredFields;

	ReflectionConfig(String name, boolean allDeclaredConstructors, boolean allPublicConstructors, boolean allDeclaredMethods, boolean allPublicMethods, boolean allPublicFields, boolean allDeclaredFields) {
		this.name = name;
		this.allDeclaredConstructors = allDeclaredConstructors;
		this.allPublicConstructors = allPublicConstructors;
		this.allDeclaredMethods = allDeclaredMethods;
		this.allPublicMethods = allPublicMethods;
		this.allPublicFields = allPublicFields;
		this.allDeclaredFields = allDeclaredFields;
	}

	public static ReflectionConfigBuilder builder() {
		return new ReflectionConfigBuilder();
	}

	public String getName() {
		return this.name;
	}

	public boolean isAllDeclaredConstructors() {
		return this.allDeclaredConstructors;
	}

	public boolean isAllPublicConstructors() {
		return this.allPublicConstructors;
	}

	public boolean isAllDeclaredMethods() {
		return this.allDeclaredMethods;
	}

	public boolean isAllPublicMethods() {
		return this.allPublicMethods;
	}

	public boolean isAllPublicFields() {
		return this.allPublicFields;
	}

	public boolean isAllDeclaredFields() {
		return this.allDeclaredFields;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAllDeclaredConstructors(boolean allDeclaredConstructors) {
		this.allDeclaredConstructors = allDeclaredConstructors;
	}

	public void setAllPublicConstructors(boolean allPublicConstructors) {
		this.allPublicConstructors = allPublicConstructors;
	}

	public void setAllDeclaredMethods(boolean allDeclaredMethods) {
		this.allDeclaredMethods = allDeclaredMethods;
	}

	public void setAllPublicMethods(boolean allPublicMethods) {
		this.allPublicMethods = allPublicMethods;
	}

	public void setAllPublicFields(boolean allPublicFields) {
		this.allPublicFields = allPublicFields;
	}

	public void setAllDeclaredFields(boolean allDeclaredFields) {
		this.allDeclaredFields = allDeclaredFields;
	}

	public boolean equals(final Object o) {
		if (o == this) return true;
		if (!(o instanceof ReflectionConfig)) return false;
		final ReflectionConfig other = (ReflectionConfig) o;
		if (!other.canEqual((Object) this)) return false;
		final Object this$type = this.getName();
		final Object other$type = other.getName();
		if (this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
		if (this.isAllDeclaredConstructors() != other.isAllDeclaredConstructors()) return false;
		if (this.isAllPublicConstructors() != other.isAllPublicConstructors()) return false;
		if (this.isAllDeclaredMethods() != other.isAllDeclaredMethods()) return false;
		if (this.isAllPublicMethods() != other.isAllPublicMethods()) return false;
		if (this.isAllPublicFields() != other.isAllPublicFields()) return false;
		if (this.isAllDeclaredFields() != other.isAllDeclaredFields()) return false;
		return true;
	}

	protected boolean canEqual(final Object other) {
		return other instanceof ReflectionConfig;
	}

	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final Object $type = this.getName();
		result = result * PRIME + ($type == null ? 43 : $type.hashCode());
		result = result * PRIME + (this.isAllDeclaredConstructors() ? 79 : 97);
		result = result * PRIME + (this.isAllPublicConstructors() ? 79 : 97);
		result = result * PRIME + (this.isAllDeclaredMethods() ? 79 : 97);
		result = result * PRIME + (this.isAllPublicMethods() ? 79 : 97);
		result = result * PRIME + (this.isAllPublicFields() ? 79 : 97);
		result = result * PRIME + (this.isAllDeclaredFields() ? 79 : 97);
		return result;
	}

	public String toString() {
		return "ReflectionConfig(type=" + this.getName() + ", allDeclaredConstructors=" + this.isAllDeclaredConstructors() + ", allPublicConstructors=" + this.isAllPublicConstructors() + ", allDeclaredMethods=" + this.isAllDeclaredMethods() + ", allPublicMethods=" + this.isAllPublicMethods() + ", allPublicFields=" + this.isAllPublicFields() + ", allDeclaredFields=" + this.isAllDeclaredFields() + ")";
	}

	public static class ReflectionConfigBuilder {
		private String type;
		private boolean allDeclaredConstructors;
		private boolean allPublicConstructors;
		private boolean allDeclaredMethods;
		private boolean allPublicMethods;
		private boolean allPublicFields;
		private boolean allDeclaredFields;

		ReflectionConfigBuilder() {
		}

		public ReflectionConfig.ReflectionConfigBuilder type(String type) {
			this.type = type;
			return this;
		}

		public ReflectionConfig.ReflectionConfigBuilder allDeclaredConstructors(boolean allDeclaredConstructors) {
			this.allDeclaredConstructors = allDeclaredConstructors;
			return this;
		}

		public ReflectionConfig.ReflectionConfigBuilder allPublicConstructors(boolean allPublicConstructors) {
			this.allPublicConstructors = allPublicConstructors;
			return this;
		}

		public ReflectionConfig.ReflectionConfigBuilder allDeclaredMethods(boolean allDeclaredMethods) {
			this.allDeclaredMethods = allDeclaredMethods;
			return this;
		}

		public ReflectionConfig.ReflectionConfigBuilder allPublicMethods(boolean allPublicMethods) {
			this.allPublicMethods = allPublicMethods;
			return this;
		}

		public ReflectionConfig.ReflectionConfigBuilder allPublicFields(boolean allPublicFields) {
			this.allPublicFields = allPublicFields;
			return this;
		}

		public ReflectionConfig.ReflectionConfigBuilder allDeclaredFields(boolean allDeclaredFields) {
			this.allDeclaredFields = allDeclaredFields;
			return this;
		}

		public ReflectionConfig build() {
			return new ReflectionConfig(type, allDeclaredConstructors, allPublicConstructors, allDeclaredMethods, allPublicMethods, allPublicFields, allDeclaredFields);
		}

		public String toString() {
			return "ReflectionConfig.ReflectionConfigBuilder(type=" + this.type + ", allDeclaredConstructors=" + this.allDeclaredConstructors + ", allPublicConstructors=" + this.allPublicConstructors + ", allDeclaredMethods=" + this.allDeclaredMethods + ", allPublicMethods=" + this.allPublicMethods + ", allPublicFields=" + this.allPublicFields + ", allDeclaredFields=" + this.allDeclaredFields + ")";
		}
	}
}
