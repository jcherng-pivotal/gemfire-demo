package io.pivotal.gemfire.demo.model.gf.key;

import java.io.Serializable;

public class ItemKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4073089398525719129L;
	private final String id;

	public ItemKey(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemKey other = (ItemKey) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ItemKey [id=" + id + "]";
	}

}
