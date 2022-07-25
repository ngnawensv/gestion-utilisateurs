package cm.belrose.gestionutilisateur.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;

/**
 * Ngnawen Samuel
 */
@Entity
@Table(name = "ROLE")
public class Role1 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "ROLE_NAME", updatable = true, nullable = false)
    private String roleName;

    public Role1() {
        super();
    }



    public Role1(String roleName) {
        super();
        this.roleName = roleName;
    }

    public Long getId() {
        return id;
    }

    @XmlElement
    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    @XmlElement
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.roleName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Role1 other = (Role1) obj;
        if (!Objects.equals(this.roleName, other.roleName)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public int compareTo(Role1 role1) {
        return this.roleName.compareTo(role1.getRoleName());
    }

    @Override
    public String toString() {
        return "Role1{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
