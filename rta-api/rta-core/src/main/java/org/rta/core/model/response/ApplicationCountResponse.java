package org.rta.core.model.response;
/**
 *	@Author sohan.maurya created on Aug 9, 2016.
 */
public class ApplicationCountResponse {

    private Long count;
    private String type;
    private String status;

    public ApplicationCountResponse() {}

    public ApplicationCountResponse(Long count, String type, String status) {
        this.count = count;
        this.type = type;
        this.status = status;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
