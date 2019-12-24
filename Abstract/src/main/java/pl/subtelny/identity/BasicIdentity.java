package pl.subtelny.identity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

public class BasicIdentity<T> implements Serializable {

	private T id;

	public BasicIdentity() {
	}

	public BasicIdentity(T id) {
		this.id = id;
	}

	public T getId() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null) {
			return false;
		}
		if (!(o instanceof BasicIdentity)) {
			return false;
		}

		BasicIdentity obj = (BasicIdentity) o;
		return new EqualsBuilder()
				.append(id, obj.id)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(id)
				.toHashCode();
	}

	@Override
	public String toString() {
		return id.toString();
	}
}
