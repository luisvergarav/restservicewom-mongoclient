package domain.model.entities;
/*
 *
 * @author Luis Vergara
 * @param <T>
 */
public abstract class BaseEntity<T> extends DefaultEntity<T> {

    private boolean isModified;

    /**
     *
     * @param id
     * @param name
     */
    public BaseEntity(String name) {
        super.name = name;
        isModified = false;
    }

    public BaseEntity() {
		// TODO Auto-generated constructor stub
	}

	/**
     *
     * @return
     */
    public boolean isIsModified() {
        return isModified;
    }

}
