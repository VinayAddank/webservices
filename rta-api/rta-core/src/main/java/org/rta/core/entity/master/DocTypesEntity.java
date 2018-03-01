package org.rta.core.entity.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.base.BaseEntity;

@Entity
@Table(name = "doc_types_master")
public class DocTypesEntity extends BaseEntity {


    private static final long serialVersionUID = -7274419569581833463L;

    @Id
    @Column(name = "doc_type_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doc_types_master_gener")
    @SequenceGenerator(name = "doc_types_master_gener", sequenceName = "doc_types_sequence", allocationSize = 1)
    private Integer docTypeId;
    
    @Column(name = "name", length = 100)
    private String name;
    
    @Column(name = "is_mandatory")
    private Boolean isMandatory;

    @Column(name = "role_name", length = 100)
    private String roleName;

    public DocTypesEntity() {}

    public DocTypesEntity(Integer docTypeId) {
        this.docTypeId = docTypeId;
    }
    public Integer getDocTypeId() {
        return docTypeId;
    }

    public void setDocTypeId(Integer docTypeId) {
        this.docTypeId = docTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsMandatory() {
        return isMandatory;
    }

    public void setIsMandatory(Boolean isMandatory) {
        this.isMandatory = isMandatory;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
