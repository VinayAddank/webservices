package org.rta.core.model.insurance;
public class IibResponseModel {
	 private String  message;

	    private Result result;

	    private String  errors;

	    private String httpStatus;

	    private String status;

	    public String  getMessage ()
	    {
	        return message;
	    }

	    public void setMessage (String message)
	    {
	        this.message = message;
	    }

	    public Result getResult ()
	    {
	        return result;
	    }

	    public void setResult (Result result)
	    {
	        this.result = result;
	    }

	    public String getErrors ()
	    {
	        return errors;
	    }

	    public void setErrors (String errors)
	    {
	        this.errors = errors;
	    }

	    public String getHttpStatus ()
	    {
	        return httpStatus;
	    }

	    public void setHttpStatus (String httpStatus)
	    {
	        this.httpStatus = httpStatus;
	    }

	    public String getStatus ()
	    {
	        return status;
	    }

	    public void setStatus (String status)
	    {
	        this.status = status;
	    }

	    @Override
	    public String toString()
	    {
	        return "ClassPojo [message = "+message+", result = "+result+", errors = "+errors+", httpStatus = "+httpStatus+", status = "+status+"]";
	    }
	   
	   
}
