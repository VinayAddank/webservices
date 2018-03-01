--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;

--
-- Name: address_history_function(bigint, character, bigint, character); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION address_history_function(addressid bigint, modifiedby character, modifiedon bigint, servicecode character) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
 BEGIN
INSERT INTO public.address_history( address_history_id, created_by, created_on, modified_by, modified_on, address_id, city, country_id, district_id, 
door_no, pin_code, state_id, status, street, type, user_id, user_type, mandal_id, service_code)
SELECT nextval ('address_history_seq'), created_by, created_on, modifiedby, modifiedon, address_id, city, country_id, district_id, 
door_no, pin_code, state_id, status, street, type, user_id, user_type, mandal_id, servicecode
FROM address
WHERE address_id = addressid;
 RETURN 'SUCCESS';
 END; $$;


ALTER FUNCTION public.address_history_function(addressid bigint, modifiedby character, modifiedon bigint, servicecode character) OWNER TO postgres;

--
-- Name: ao_sta(bigint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION ao_sta(bigint) RETURNS integer
    LANGUAGE sql
    AS $_$
       SELECT status FROM vehicle_rc_history WHERE vehicle_rc_id = $1 and rta_employee_type = 7 and iteration in ( Select iteration from vehicle_rc where vehicle_rc_id = $1) order by modified_on desc limit 1 ;
$_$;


ALTER FUNCTION public.ao_sta(bigint) OWNER TO postgres;

--
-- Name: ao_usr(bigint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION ao_usr(bigint) RETURNS bigint
    LANGUAGE sql
    AS $_$
       SELECT user_id FROM vehicle_rc_history WHERE vehicle_rc_id = $1 and rta_employee_type = 7 and iteration in ( Select iteration from vehicle_rc where vehicle_rc_id = $1) order by modified_on desc limit 1 ;
$_$;


ALTER FUNCTION public.ao_usr(bigint) OWNER TO postgres;

--
-- Name: attachment_details_history_function(bigint, character, bigint, character); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION attachment_details_history_function(attachmentid bigint, modifiedby character, modifiedon bigint, servicecode character) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
 BEGIN
INSERT INTO public.attachment_details_history( attachment_detail_history_id, created_by, created_on, modified_by, modified_on, attachment_detail_id, 
attachment_from, attachment_title, file_name, source, status, user_role, doc_types_id, vehicle_rc_id, service_code)
SELECT nextval ('attachment_details_history_sequence'), created_by, created_on, modifiedby, modifiedon, attachment_detail_id, 
attachment_from, attachment_title, file_name, source, status, user_role, doc_types_id, vehicle_rc_id, servicecode
FROM attachment_details
WHERE attachment_detail_id = attachmentid;
 RETURN 'SUCCESS';
 END; $$;


ALTER FUNCTION public.attachment_details_history_function(attachmentid bigint, modifiedby character, modifiedon bigint, servicecode character) OWNER TO postgres;

--
-- Name: cco_sta(bigint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION cco_sta(bigint) RETURNS integer
    LANGUAGE sql
    AS $_$
       SELECT status FROM vehicle_rc_history WHERE vehicle_rc_id = $1 and rta_employee_type = 3 and iteration in ( Select iteration from vehicle_rc where vehicle_rc_id = $1) order by modified_on desc limit 1 ;
$_$;


ALTER FUNCTION public.cco_sta(bigint) OWNER TO postgres;

--
-- Name: cco_usr(bigint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION cco_usr(bigint) RETURNS bigint
    LANGUAGE sql
    AS $_$
       SELECT user_id FROM vehicle_rc_history WHERE vehicle_rc_id = $1 and rta_employee_type = 3 and iteration in ( Select iteration from vehicle_rc where vehicle_rc_id = $1) order by modified_on desc limit 1 ;
$_$;


ALTER FUNCTION public.cco_usr(bigint) OWNER TO postgres;

--
-- Name: cust_corporate_details_history_function(bigint, character, bigint, character); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION cust_corporate_details_history_function(userid bigint, modifiedby character, modifiedon bigint, servicecode character) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
 BEGIN
INSERT INTO cust_corporate_details_history( cust_corp_dtls_history_id, cust_corp_dtls_id, created_by, created_on, modified_by, modified_on, 
            aadhar_no, aternate_email, aternate_phone_no, company_name, display_name,  email, emergency_no, mobile_no, pan_no, permanent_phone_no, 
            phone_no, represented_by, status, vehicle_reg_rta, vehicle_reg_rta_code,  vehicle_rc_id, blood_group, service_code)
SELECT nextval ('user_history_seq'), cust_corp_dtls_id, created_by, created_on, modifiedby, modifiedon, aadhar_no, aternate_email, 
aternate_phone_no, company_name, display_name,  email, emergency_no, mobile_no, pan_no, permanent_phone_no, phone_no, 
represented_by, status, vehicle_reg_rta, vehicle_reg_rta_code,  vehicle_rc_id, blood_group, servicecode
FROM cust_corporate_details
WHERE cust_corp_dtls_id = userid;
 RETURN 'SUCCESS';
 END; $$;


ALTER FUNCTION public.cust_corporate_details_history_function(userid bigint, modifiedby character, modifiedon bigint, servicecode character) OWNER TO postgres;

--
-- Name: cust_individual_details_history_function(bigint, character, bigint, character); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION cust_individual_details_history_function(userid bigint, modifiedby character, modifiedon bigint, servicecode character) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
 BEGIN
INSERT INTO public.cust_individual_details_history(cust_ind_dtl_history_id, created_by, created_on, modified_by, modified_on, aadhar_no, address_person, 
    alternate_email, alternate_mobile_no, blood_group, cust_ind_dtl_id, date_of_birth, display_name, email_id, emergency_no, father_name, first_name, gender,
    is_dependent_addrs, is_disabled, is_invalid_carriage, is_same_as_addr_aadhar, is_second_vehicle, mobile_no, name_of_dependent, pan_no, passport_no, 
    passport_valid_upto, permanent_phone_no, qualification_id, status, sur_name, vechicle_reg_rta, vehicle_reg_rta_code, vehicle_rc_id, service_code)
    SELECT nextval ('user_history_seq'), created_by, created_on, modifiedby, modifiedon, aadhar_no, address_person, 
    alternate_email, alternate_mobile_no, blood_group, cust_ind_dtl_id, date_of_birth, display_name, email_id, emergency_no, father_name, first_name, gender,
    is_dependent_addrs, is_disabled, is_invalid_carriage, is_same_as_addr_aadhar, is_second_vehicle, mobile_no, name_of_dependent, pan_no, passport_no, 
    passport_valid_upto, permanent_phone_no, qualification_id, status, sur_name, vechicle_reg_rta, vehicle_reg_rta_code, vehicle_rc_id, servicecode
FROM cust_individual_details
WHERE cust_ind_dtl_id = userid;
 RETURN 'SUCCESS';
 END; $$;


ALTER FUNCTION public.cust_individual_details_history_function(userid bigint, modifiedby character, modifiedon bigint, servicecode character) OWNER TO postgres;

--
-- Name: iter(bigint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION iter(bigint) RETURNS integer
    LANGUAGE sql
    AS $_$
       SELECT max(iteration) FROM vehicle_rc_history WHERE vehicle_rc_id = $1 ;
$_$;


ALTER FUNCTION public.iter(bigint) OWNER TO postgres;

--
-- Name: mvi_sta(bigint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION mvi_sta(bigint) RETURNS integer
    LANGUAGE sql
    AS $_$
       SELECT status FROM vehicle_rc_history WHERE vehicle_rc_id = $1 and rta_employee_type = 6 and iteration in ( Select iteration from vehicle_rc where vehicle_rc_id = $1) order by modified_on desc limit 1 ;
$_$;


ALTER FUNCTION public.mvi_sta(bigint) OWNER TO postgres;

--
-- Name: mvi_usr(bigint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION mvi_usr(bigint) RETURNS bigint
    LANGUAGE sql
    AS $_$
       SELECT user_id FROM vehicle_rc_history WHERE vehicle_rc_id = $1 and rta_employee_type = 6 and iteration in ( Select iteration from vehicle_rc where vehicle_rc_id = $1) order by modified_on desc limit 1 ;
$_$;


ALTER FUNCTION public.mvi_usr(bigint) OWNER TO postgres;

--
-- Name: permanent_address_history_function(bigint, character, bigint, character); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION permanent_address_history_function(addresshistoryid bigint, modifiedby character, modifiedon bigint, servicecode character) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
 BEGIN
INSERT INTO public.permanent_address_history(  p_address_history_id, p_address_id, created_by, created_on, modified_by, modified_on, address_type, 
city, country,district, door_no, mandal, pin_code, post_office, state, status, street, user_id, user_type, service_code)
SELECT nextval ('p_address_history_seq'), p_address_id, created_by, created_on, modifiedby, modifiedon, address_type, city, 
country, district, door_no, mandal, pin_code, post_office, state, status, street, user_id, user_type, servicecode
FROM permanent_address
WHERE p_address_id = addresshistoryid;
 RETURN 'SUCCESS';
 END; $$;


ALTER FUNCTION public.permanent_address_history_function(addresshistoryid bigint, modifiedby character, modifiedon bigint, servicecode character) OWNER TO postgres;

--
-- Name: rto_sta(bigint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION rto_sta(bigint) RETURNS integer
    LANGUAGE sql
    AS $_$
       SELECT status FROM vehicle_rc_history WHERE vehicle_rc_id = $1 and rta_employee_type = 8 and iteration in ( Select iteration from vehicle_rc where vehicle_rc_id = $1) order by modified_on desc limit 1 ;
$_$;


ALTER FUNCTION public.rto_sta(bigint) OWNER TO postgres;

--
-- Name: rto_usr(bigint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION rto_usr(bigint) RETURNS bigint
    LANGUAGE sql
    AS $_$
       SELECT user_id FROM vehicle_rc_history WHERE vehicle_rc_id = $1 and rta_employee_type = 8 and iteration in ( Select iteration from vehicle_rc where vehicle_rc_id = $1) order by modified_on desc limit 1 ;
$_$;


ALTER FUNCTION public.rto_usr(bigint) OWNER TO postgres;

--
-- Name: vehicle_details_history_function(bigint, character, bigint, character); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION vehicle_details_history_function(vehicledtlid bigint, modifiedby character, modifiedon bigint, servicecode character) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
 BEGIN
INSERT INTO public.vehicle_details_history( vehicle_dtl_history_id, vehicle_dtl_id, created_by, created_on, modified_by, modified_on, alteration_category, body_type_update, 
chassis_no, color, completion_date, engine_no, fuel_used, height_update, length_update, maker_class, maker_name, mfg_date, rlw, seating_capacity,
ulw, vehcle_category, vehicle_class, vehicle_number, vehicle_sub_class, wheelbase, width_update, tax_type_id, vehicle_rc_id, service_code)
SELECT nextval ('vehicle_dtl_history_seq'), vehicle_dtl_id, created_by, created_on, modifiedby, modifiedon, alteration_category, body_type_update, 
chassis_no, color, completion_date, engine_no, fuel_used, height_update, length_update, maker_class, maker_name, mfg_date, rlw, seating_capacity,
ulw, vehcle_category, vehicle_class, vehicle_number, vehicle_sub_class, wheelbase, width_update, tax_type_id, vehicle_rc_id, servicecode
FROM vehicle_details
WHERE vehicle_dtl_id = vehicledtlid;
 RETURN 'SUCCESS';
 END; $$;


ALTER FUNCTION public.vehicle_details_history_function(vehicledtlid bigint, modifiedby character, modifiedon bigint, servicecode character) OWNER TO postgres;

--
-- Name: vehicle_rc_change_history_function(bigint, character, bigint, character); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION vehicle_rc_change_history_function(vehiclercid bigint, modifiedby character, modifiedon bigint, servicecode character) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
 BEGIN
INSERT INTO public.vehicle_rc_change_history(vehicle_rc_change_history_id, created_by, created_on, modified_by, modified_on, aadhar_no, chassis_no, 
current_step, doc_upload_consent, is_aadhar_verified, is_migrated, is_printed, iteration, ownership_type, pr_issue_time, pr_number, 
pr_status, pr_type, process_status, rc_print_date, tr_issue_time, tr_number, tr_status, vehicle_rc_id, reg_category, rta_office_id, 
user_id, pr_expire_date, service_code)
SELECT nextval ('vehicle_change_history_seq'), created_by, created_on, modified_by, modified_on, aadhar_no, chassis_no, current_step, doc_upload_consent, 
is_aadhar_verified, is_migrated, is_printed, iteration, ownership_type, pr_issue_time, pr_number, pr_status, pr_type, process_status, rc_print_date, 
tr_issue_time, tr_number, tr_status, vehicle_rc_id, reg_category, rta_office_id, user_id, pr_expire_date, servicecode
FROM vehicle_rc
WHERE vehicle_rc_id = vehiclercid;
 RETURN 'SUCCESS';
 END; $$;


ALTER FUNCTION public.vehicle_rc_change_history_function(vehiclercid bigint, modifiedby character, modifiedon bigint, servicecode character) OWNER TO postgres;

--
-- Name: aadhaar_log_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE aadhaar_log_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE aadhaar_log_seq OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: aadhaar_logs; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE aadhaar_logs (
    id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    req_agency_code character varying(255),
    req_agency_name character varying(255),
    req_attempt_type character varying(255),
    req_auth_type character varying(255),
    req_client_date_time character varying(255),
    req_consent character varying(255),
    req_consent_me character varying(255),
    req_device_code character varying(255),
    req_device_name character varying(255),
    req_fdc character varying(255),
    req_provider character varying(255),
    req_server_date_time character varying(255),
    req_service character varying(255),
    req_tid character varying(255),
    req_time character varying(255),
    req_uid character varying(255),
    resp_auth_date character varying(255),
    resp_auth_error_code character varying(255),
    resp_auth_status character varying(255),
    resp_auth_transaction_code character varying(255),
    resp_ksa_kua_txn character varying(255),
    resp_mandal character varying(255),
    resp_name character varying(255),
    resp_state_code character varying(255),
    resp_time character varying(255),
    req_end_point_url character varying(255),
    req_rds_ver character varying,
    req_rds_id character varying,
    req_dp_id character varying,
    req_dc character varying,
    req_mi character varying,
    req_mc character varying
);


ALTER TABLE aadhaar_logs OWNER TO postgres;

--
-- Name: aadhaar_master; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE aadhaar_master (
    id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    ksakuatxn character varying(255),
    auth_date character varying(255),
    auth_status character varying(255),
    authtransaction_code character varying(255),
    base64file text,
    father_name character varying(255),
    district character varying(255),
    district_name character varying(255),
    date_of_birth character varying(255),
    gender character varying(255),
    house character varying(255),
    lc character varying(255),
    mandal character varying(255),
    mandal_name character varying(255),
    name character varying(255),
    pincode character varying(255),
    po character varying(255),
    state_code character varying(255),
    street character varying(255),
    sub_dist character varying(255),
    uid bigint,
    village character varying(255),
    village_name character varying(255),
    vtc character varying(255)
);


ALTER TABLE aadhaar_master OWNER TO postgres;

--
-- Name: aadhar_master_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE aadhar_master_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE aadhar_master_seq OWNER TO postgres;

--
-- Name: address; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE address (
    address_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    city character varying(255),
    country_id bigint,
    district_id bigint,
    door_no character varying(255),
    state_id bigint,
    status boolean,
    street character varying(255),
    type character varying(255),
    user_id bigint,
    user_type character varying(50),
    mandal_id bigint NOT NULL,
    pin_code character varying(255),
    version integer DEFAULT 0 NOT NULL
);


ALTER TABLE address OWNER TO postgres;

--
-- Name: address_history; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE address_history (
    address_history_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    address_id bigint,
    city character varying(255),
    country_id bigint,
    district_id bigint,
    door_no character varying(255),
    pin_code character varying(255),
    service_code character varying(255),
    state_id bigint,
    status boolean,
    street character varying(255),
    type character varying(255),
    user_id bigint,
    user_type character varying(50),
    mandal_id bigint NOT NULL,
    old_pkey character varying(255)
);


ALTER TABLE address_history OWNER TO postgres;

--
-- Name: address_history_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE address_history_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE address_history_seq OWNER TO postgres;

--
-- Name: address_proof_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE address_proof_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE address_proof_seq OWNER TO postgres;

--
-- Name: address_proof_type; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE address_proof_type (
    address_proof_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    name character varying(255),
    status boolean,
    code character varying(50)
);


ALTER TABLE address_proof_type OWNER TO postgres;

--
-- Name: address_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE address_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE address_seq OWNER TO postgres;

--
-- Name: affixation_center_master; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE affixation_center_master (
    affixation_center_id integer NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    affixation_center_code character varying(20) NOT NULL,
    mandal_code integer NOT NULL,
    status boolean NOT NULL,
    district_id integer
);


ALTER TABLE affixation_center_master OWNER TO postgres;

--
-- Name: affixation_center_master_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE affixation_center_master_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE affixation_center_master_sequence OWNER TO postgres;

--
-- Name: age_group_ref; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE age_group_ref (
    age_group_cd integer NOT NULL,
    created_by character varying(50),
    created_on date,
    modified_by character varying(50),
    modified_on date,
    age_end integer,
    age_group_desc character varying(255),
    age_start integer,
    is_active character varying(255)
);


ALTER TABLE age_group_ref OWNER TO postgres;

--
-- Name: alteration_agency_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE alteration_agency_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE alteration_agency_seq OWNER TO postgres;

--
-- Name: alteration_agency_users; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE alteration_agency_users (
    alteration_agency_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    class_of_vehicle character varying(255),
    name character varying(255),
    user_id bigint
);


ALTER TABLE alteration_agency_users OWNER TO postgres;

--
-- Name: alteration_cov_mapping; Type: TABLE; Schema: public; Owner: rtamigprod; Tablespace: 
--

CREATE TABLE alteration_cov_mapping (
    alteration_cov_mapping_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    non_transport_cov_code character varying(255),
    transport_cov_code character varying(255)
);


ALTER TABLE alteration_cov_mapping OWNER TO rtamigprod;

--
-- Name: alteration_cov_mapping_id_seq; Type: SEQUENCE; Schema: public; Owner: rtamigprod
--

CREATE SEQUENCE alteration_cov_mapping_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE alteration_cov_mapping_id_seq OWNER TO rtamigprod;

--
-- Name: app_version; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE app_version (
    version_id integer NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    app_name character varying(255) NOT NULL,
    app_url character varying(255),
    message character varying(255),
    status integer NOT NULL,
    ver_major integer NOT NULL,
    ver_minor integer NOT NULL,
    ver_revision integer NOT NULL
);


ALTER TABLE app_version OWNER TO postgres;

--
-- Name: app_version_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE app_version_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE app_version_seq OWNER TO postgres;

--
-- Name: application_slot_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE application_slot_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE application_slot_seq OWNER TO postgres;

--
-- Name: application_slots; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE application_slots (
    application_slot_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    application_number character varying(255),
    end_time bigint,
    is_enabled boolean,
    scheduled_date bigint,
    scheduled_time bigint,
    service_code character varying(255),
    slot_id bigint,
    slot_service_type character varying(255),
    slot_status character varying(255),
    start_time bigint,
    vehicle_rc_id bigint
);


ALTER TABLE application_slots OWNER TO postgres;

--
-- Name: attachment_details; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE attachment_details (
    attachment_detail_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    attachment_from integer NOT NULL,
    attachment_title character varying(200) NOT NULL,
    file_name character varying(200) NOT NULL,
    source character varying(255) NOT NULL,
    status integer NOT NULL,
    doc_types_id integer NOT NULL,
    vehicle_rc_id bigint NOT NULL,
    user_role integer,
    version integer DEFAULT 0 NOT NULL
);


ALTER TABLE attachment_details OWNER TO postgres;

--
-- Name: attachment_details_history; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE attachment_details_history (
    attachment_detail_history_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    attachment_detail_id bigint,
    attachment_from integer NOT NULL,
    attachment_title character varying(200) NOT NULL,
    file_name character varying(200) NOT NULL,
    service_code character varying(255),
    source character varying(255) NOT NULL,
    status integer NOT NULL,
    user_role integer,
    doc_types_id integer NOT NULL,
    vehicle_rc_id bigint NOT NULL
);


ALTER TABLE attachment_details_history OWNER TO postgres;

--
-- Name: attachment_details_history_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE attachment_details_history_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE attachment_details_history_sequence OWNER TO postgres;

--
-- Name: attachment_details_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE attachment_details_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE attachment_details_sequence OWNER TO postgres;

--
-- Name: bharat_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE bharat_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bharat_seq OWNER TO postgres;

--
-- Name: body_builder_users; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE body_builder_users (
    body_builder_user_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    name character varying(255),
    user_id bigint
);


ALTER TABLE body_builder_users OWNER TO postgres;

--
-- Name: body_builder_users_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE body_builder_users_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE body_builder_users_seq OWNER TO postgres;

--
-- Name: body_type_master; Type: TABLE; Schema: public; Owner: rtamigprod; Tablespace: 
--

CREATE TABLE body_type_master (
    body_type_master_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    body_type character varying(255),
    is_active boolean
);


ALTER TABLE body_type_master OWNER TO rtamigprod;

--
-- Name: body_type_master_id_seq; Type: SEQUENCE; Schema: public; Owner: rtamigprod
--

CREATE SEQUENCE body_type_master_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE body_type_master_id_seq OWNER TO rtamigprod;

--
-- Name: cess_fee_details; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cess_fee_details (
    cess_fee_dtl_id bigint NOT NULL,
    cess_fee double precision NOT NULL,
    cess_fee_valid_upto bigint,
    vehicle_rc_id bigint NOT NULL,
    transaction_id bigint,
    reg_type integer,
    version integer DEFAULT 0 NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint
);


ALTER TABLE cess_fee_details OWNER TO postgres;

--
-- Name: cess_fee_details_history; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cess_fee_details_history (
    cess_dtl_hist_id bigint NOT NULL,
    cess_fee double precision NOT NULL,
    cess_fee_valid_upto bigint,
    vehicle_rc_id bigint NOT NULL,
    transaction_id bigint,
    reg_type integer,
    version integer DEFAULT 0 NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint
);


ALTER TABLE cess_fee_details_history OWNER TO postgres;

--
-- Name: cess_fee_dtl_hist_seq; Type: SEQUENCE; Schema: public; Owner: rtamigprod
--

CREATE SEQUENCE cess_fee_dtl_hist_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE cess_fee_dtl_hist_seq OWNER TO rtamigprod;

--
-- Name: cess_fee_dtl_seq; Type: SEQUENCE; Schema: public; Owner: rtamigprod
--

CREATE SEQUENCE cess_fee_dtl_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE cess_fee_dtl_seq OWNER TO rtamigprod;

--
-- Name: cfx_note_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE cfx_note_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE cfx_note_seq OWNER TO postgres;

--
-- Name: cfx_notes; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cfx_notes (
    cfx_note_id bigint NOT NULL,
    created_by character varying(50),
    created_on date,
    modified_by character varying(50),
    modified_on date,
    application_number character varying(255),
    is_cfx_endorse_in_rc boolean,
    cfx_number character varying(255),
    defect_noticed character varying(255),
    destination character varying(255),
    dl_number character varying(255),
    driver_name character varying(255),
    is_accident boolean,
    max_speed integer,
    place_of_checking character varying(255),
    pr_number character varying(255),
    status integer,
    user_id bigint,
    vcr_number character varying(255),
    time_of_checking bigint
);


ALTER TABLE cfx_notes OWNER TO postgres;

--
-- Name: cfx_txn_dtl; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cfx_txn_dtl (
    cfx_txn_dtl_id bigint NOT NULL,
    amount double precision,
    cfx_payment_id character varying(255),
    cfx_txn_id character varying(255),
    customer_email character varying(255),
    customer_name character varying(255),
    customer_phone character varying(255),
    date_time character varying(255),
    description character varying(255),
    payment_status integer,
    pg_payment_token character varying(255),
    policy_doc character varying(255),
    policy_number character varying(255),
    policy_status integer,
    pooling_ac_number character varying(255),
    rta_cfx_txn_id character varying(255),
    transaction_detail bigint NOT NULL,
    vehicle_rc_id bigint NOT NULL
);


ALTER TABLE cfx_txn_dtl OWNER TO postgres;

--
-- Name: cfx_txn_dtl_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE cfx_txn_dtl_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE cfx_txn_dtl_seq OWNER TO postgres;

--
-- Name: challan_number; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE challan_number (
    challan_number_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    consume_number bigint,
    end_number bigint,
    series bigint,
    start_number bigint,
    status integer,
    treasury_code character varying(255),
    version integer
);


ALTER TABLE challan_number OWNER TO postgres;

--
-- Name: challan_number_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE challan_number_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE challan_number_seq OWNER TO postgres;

--
-- Name: cms_sync_vehicle; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cms_sync_vehicle (
    sync_id bigint NOT NULL,
    created_on bigint,
    message character varying(1000),
    modified_on bigint,
    response_code character varying(255),
    vehicle_number character varying(20),
    vehicle_rc_id bigint,
    service_code character varying(50)
);


ALTER TABLE cms_sync_vehicle OWNER TO postgres;

--
-- Name: cms_sync_vehicle_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE cms_sync_vehicle_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE cms_sync_vehicle_sequence OWNER TO postgres;

--
-- Name: country; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE country (
    country_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    name character varying(255),
    status boolean,
    code character varying(50)
);


ALTER TABLE country OWNER TO postgres;

--
-- Name: country_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE country_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE country_id_seq OWNER TO postgres;

--
-- Name: currenttaxjoinperiodic; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE currenttaxjoinperiodic (
    pt_tax_dtl_id bigint,
    pt_tax_valid_upto date,
    pt_vehicle_rc_id bigint,
    pt_vehicle_reg_no character varying(50),
    pt_tax_amt double precision,
    tbu_vehicle_tax_dtl_id bigint
);


ALTER TABLE currenttaxjoinperiodic OWNER TO postgres;

--
-- Name: currenttaxupddata; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE currenttaxupddata (
    pt_vehicle_rc_id bigint,
    total_tax double precision,
    rc_update_count bigint
);


ALTER TABLE currenttaxupddata OWNER TO postgres;

--
-- Name: cust_corporate_details; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cust_corporate_details (
    cust_corp_dtls_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    aadhar_no character varying(255),
    aternate_email character varying(255),
    aternate_phone_no character varying(255),
    company_name character varying(255),
    display_name character varying(255),
    email character varying(255),
    emergency_no character varying(255),
    mobile_no character varying(255),
    pan_no character varying(255),
    permanent_phone_no character varying(255),
    phone_no character varying(255),
    represented_by character varying(255),
    status character varying(255),
    vehicle_reg_rta character varying(255),
    vehicle_reg_rta_code character varying(255),
    vehicle_rc_id bigint,
    blood_group character varying(10),
    version integer DEFAULT 0 NOT NULL,
    old_pkey character varying(255)
);


ALTER TABLE cust_corporate_details OWNER TO postgres;

--
-- Name: cust_corporate_details_history; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cust_corporate_details_history (
    cust_corp_dtls_history_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    aadhar_no character varying(255),
    aternate_email character varying(255),
    aternate_phone_no character varying(255),
    blood_group character varying(10),
    company_name character varying(255),
    cust_corp_dtls_id bigint,
    display_name character varying(255),
    email character varying(255),
    emergency_no character varying(255),
    mobile_no character varying(255),
    ownership_end_date bigint,
    ownership_start_date bigint,
    pan_no character varying(255),
    permanent_phone_no character varying(255),
    phone_no character varying(255),
    represented_by character varying(255),
    service_code character varying(255),
    status character varying(255),
    vehicle_reg_rta character varying(255),
    vehicle_reg_rta_code character varying(255),
    vehicle_rc_id bigint
);


ALTER TABLE cust_corporate_details_history OWNER TO postgres;

--
-- Name: cust_individual_details; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cust_individual_details (
    cust_ind_dtl_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    aadhar_no character varying(255),
    alternate_email character varying(255),
    alternate_mobile_no character varying(255),
    date_of_birth character varying(255) NOT NULL,
    display_name character varying(255) NOT NULL,
    email_id character varying(255) NOT NULL,
    emergency_no character varying(255),
    father_name character varying(255),
    first_name character varying(255) NOT NULL,
    gender character varying(255) NOT NULL,
    is_disabled boolean,
    is_invalid_carriage boolean,
    is_same_as_addr_aadhar boolean,
    is_second_vehicle boolean,
    mobile_no character varying(255) NOT NULL,
    pan_no character varying(255),
    passport_no character varying(255),
    passport_valid_upto character varying(255),
    permanent_phone_no character varying(255),
    qualification_id bigint,
    status character varying(255),
    sur_name character varying(255) NOT NULL,
    vechicle_reg_rta character varying(255),
    vehicle_reg_rta_code character varying(255),
    vehicle_rc_id bigint,
    blood_group character varying(10),
    address_person character varying(50),
    is_dependent_addrs boolean DEFAULT false,
    name_of_dependent character varying(255),
    version integer DEFAULT 0 NOT NULL,
    old_pkey character varying(255)
);


ALTER TABLE cust_individual_details OWNER TO postgres;

--
-- Name: cust_individual_details_history; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cust_individual_details_history (
    cust_ind_dtl_history_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    aadhar_no character varying(255),
    address_person character varying(50),
    alternate_email character varying(255),
    alternate_mobile_no character varying(255),
    blood_group character varying(10),
    cust_ind_dtl_id bigint,
    date_of_birth character varying(255) NOT NULL,
    display_name character varying(255) NOT NULL,
    email_id character varying(255) NOT NULL,
    emergency_no character varying(255),
    father_name character varying(255) NOT NULL,
    first_name character varying(255) NOT NULL,
    gender character varying(255) NOT NULL,
    is_dependent_addrs boolean DEFAULT false,
    is_disabled boolean,
    is_invalid_carriage boolean,
    is_same_as_addr_aadhar boolean,
    is_second_vehicle boolean,
    mobile_no character varying(255) NOT NULL,
    name_of_dependent character varying(255),
    ownership_end_date bigint,
    ownership_start_date bigint,
    pan_no character varying(255),
    passport_no character varying(255),
    passport_valid_upto character varying(255),
    permanent_phone_no character varying(255),
    qualification_id bigint,
    service_code character varying(255),
    status character varying(255),
    sur_name character varying(255) NOT NULL,
    vechicle_reg_rta character varying(255),
    vehicle_reg_rta_code character varying(255),
    vehicle_rc_id bigint
);


ALTER TABLE cust_individual_details_history OWNER TO postgres;

--
-- Name: dealer; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE dealer (
    dealer_id bigint NOT NULL,
    fax character varying(255),
    name character varying(255),
    parent_id bigint,
    user_id bigint,
    dealer_sign character varying(255),
    eligible_to_pay boolean DEFAULT true,
    lat double precision,
    lng double precision
);


ALTER TABLE dealer OWNER TO postgres;

--
-- Name: dealer_inv_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE dealer_inv_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dealer_inv_seq OWNER TO postgres;

--
-- Name: dealer_invoice; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE dealer_invoice (
    dealer_invc_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    hsrp_fee double precision,
    invoice_amt double precision,
    invoice_date bigint,
    invoice_no character varying(255),
    second_vehicle_taxamt double precision,
    status integer,
    total_amount double precision,
    user_id bigint NOT NULL,
    vehicle_rc_id bigint NOT NULL,
    version integer DEFAULT 0 NOT NULL
);


ALTER TABLE dealer_invoice OWNER TO postgres;

--
-- Name: dealer_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE dealer_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dealer_seq OWNER TO postgres;

--
-- Name: designation; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE designation (
    designation_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    name character varying(255),
    status boolean,
    code character varying(50)
);


ALTER TABLE designation OWNER TO postgres;

--
-- Name: designation_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE designation_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE designation_seq OWNER TO postgres;

--
-- Name: district; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE district (
    district_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    name character varying(255),
    status boolean,
    code character varying(50),
    state_id bigint
);


ALTER TABLE district OWNER TO postgres;

--
-- Name: district_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE district_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE district_id_seq OWNER TO postgres;

--
-- Name: doc_permission; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE doc_permission (
    doc_permission_id integer NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    is_mandatory boolean,
    ownership_type integer,
    registration_category_type integer,
    vehicle_class integer,
    doc_types_id integer NOT NULL
);


ALTER TABLE doc_permission OWNER TO postgres;

--
-- Name: doc_permission_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE doc_permission_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE doc_permission_seq OWNER TO postgres;

--
-- Name: doc_types_master; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE doc_types_master (
    doc_type_id integer NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    is_mandatory boolean,
    name character varying(100),
    role_name character varying(100)
);


ALTER TABLE doc_types_master OWNER TO postgres;

--
-- Name: doc_types_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE doc_types_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE doc_types_sequence OWNER TO postgres;

--
-- Name: drivers_license_dtls; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE drivers_license_dtls (
    dl_sequence_id integer NOT NULL,
    licence_holder_id bigint NOT NULL,
    created_by character varying(50),
    created_on date,
    modified_by character varying(50),
    modified_on date,
    app_id character varying(255),
    application_id character varying(255),
    badge_issued_date date,
    badge_rta_office_code character varying(255),
    date_of_first_issue date,
    dl_badge_no character varying(255),
    dl_issued_date date,
    dl_no character varying(255),
    dl_type character varying(255),
    dl_vehicle_class_code character varying(255),
    driving_school_license_no character varying(255),
    is_trained character varying(255),
    llr_no character varying(255),
    module_cd character varying(255),
    observation character varying(255),
    photo_attachment_id bigint,
    planned_valid_from date,
    planned_valid_to date,
    reference_id character varying(255),
    renewal_flag character varying(255),
    retest_flag character varying(255),
    retest_reason character varying(255),
    sign_attachment_id bigint,
    status_code character varying(255),
    status_date date,
    status_remarks character varying(255),
    status_updated_by character varying(255),
    status_updated_on date,
    status_valid_from date,
    status_valid_to date,
    test_date date,
    test_exempted character varying(255),
    test_exempted_reason character varying(255),
    test_id character varying(255),
    test_result character varying(255),
    ticket_details character varying(255),
    valid_flg character varying(255),
    valid_from date,
    valid_to date,
    ao_user_id bigint,
    mvi_user_id bigint,
    rta_office_code bigint
);


ALTER TABLE drivers_license_dtls OWNER TO postgres;

--
-- Name: drivers_license_dtls_history; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE drivers_license_dtls_history (
    drivers_license_hist_id bigint NOT NULL,
    created_by character varying(50),
    created_on date,
    modified_by character varying(50),
    modified_on date,
    app_id character varying(255),
    application_id character varying(255),
    approved_ao character varying(255),
    approved_mvi character varying(255),
    badge_issued_date date,
    badge_rta_office_code character varying(255),
    date_of_first_issue date,
    dl_issued_date date,
    dl_no character varying(255),
    dl_sequence_id integer,
    dl_type character varying(255),
    dl_vehicle_class_code character varying(255),
    driving_school_license_no character varying(255),
    is_trained character varying(255),
    licence_holder_id bigint,
    llr_no character varying(255),
    module_cd character varying(255),
    observation character varying(255),
    photo_attachment_id bigint,
    planned_valid_from date,
    planned_valid_to date,
    reference_id character varying(255),
    renewal_flag character varying(255),
    retest_flag character varying(255),
    retest_reason character varying(255),
    service_code character varying(255),
    sign_attachment_id bigint,
    status_code character varying(255),
    status_date date,
    status_remarks character varying(255),
    status_updated_by character varying(255),
    status_updated_on date,
    status_valid_from date,
    status_valid_to date,
    test_date date,
    test_exempted character varying(255),
    test_exempted_reason character varying(255),
    test_id character varying(255),
    test_result character varying(255),
    ticket_details character varying(255),
    valid_flg character varying(255),
    valid_from date,
    valid_to date,
    rta_office_code bigint
);


ALTER TABLE drivers_license_dtls_history OWNER TO postgres;

--
-- Name: drivers_license_hist_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE drivers_license_hist_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE drivers_license_hist_seq OWNER TO postgres;

--
-- Name: driving_institute_users; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE driving_institute_users (
    driving_institute_users_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    name character varying(255),
    no_of_cars integer,
    no_of_driving_instructors integer,
    user_id bigint
);


ALTER TABLE driving_institute_users OWNER TO postgres;

--
-- Name: driving_institute_users_info; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE driving_institute_users_info (
    driving_institute_users_info_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    number character varying(255),
    type integer,
    driving_institute_users_id bigint
);


ALTER TABLE driving_institute_users_info OWNER TO postgres;

--
-- Name: driving_institute_users_info_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE driving_institute_users_info_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE driving_institute_users_info_seq OWNER TO postgres;

--
-- Name: driving_institute_users_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE driving_institute_users_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE driving_institute_users_seq OWNER TO postgres;

--
-- Name: event_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE event_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE event_id_seq OWNER TO postgres;

--
-- Name: event_log; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE event_log (
    event_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    pr_attachement character varying(255),
    pr_email_notified boolean,
    pr_sms_notified boolean,
    tr_attachment character varying(255),
    tr_email_notified boolean,
    tr_sms_notified boolean,
    vehicle_rc_id bigint,
    pr_sms_iteration integer DEFAULT 0,
    pr_email_iteration integer DEFAULT 0,
    tr_sms_iteration integer DEFAULT 0,
    tr_email_iteration integer DEFAULT 0
);


ALTER TABLE event_log OWNER TO postgres;

--
-- Name: finance_app_status; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE finance_app_status (
    id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    agrement_letter_url character varying(255),
    finance_status integer,
    requested_date bigint,
    sanction_letter_url character varying(255),
    status_changed_date bigint,
    token_id character varying(255),
    user_id bigint NOT NULL
);


ALTER TABLE finance_app_status OWNER TO postgres;

--
-- Name: finance_approval_history_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE finance_approval_history_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE finance_approval_history_seq OWNER TO postgres;

--
-- Name: finance_approve_details; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE finance_approve_details (
    id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    aggrement_url character varying(255),
    approved_date bigint,
    first_approver_aadhar character varying(255),
    first_approver_name character varying(255),
    second_approver_aadhar character varying(255),
    second_approver_name character varying(255),
    finance_other_service bigint NOT NULL
);


ALTER TABLE finance_approve_details OWNER TO postgres;

--
-- Name: finance_approve_details_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE finance_approve_details_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE finance_approve_details_seq OWNER TO postgres;

--
-- Name: finance_approve_history; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE finance_approve_history (
    id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    status integer,
    comments character varying(255),
    finance_id bigint,
    service_type integer,
    approver_id bigint NOT NULL,
    vehicle_rc_id bigint NOT NULL
);


ALTER TABLE finance_approve_history OWNER TO postgres;

--
-- Name: finance_branch; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE finance_branch (
    id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    status integer,
    branch_address character varying(255),
    city character varying(255),
    name character varying(255),
    district_id bigint,
    user_id bigint NOT NULL,
    mandal_id bigint,
    state_id bigint,
    old_pkey character varying(255)
);


ALTER TABLE finance_branch OWNER TO postgres;

--
-- Name: finance_branch_employee; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE finance_branch_employee (
    id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    active_status integer,
    branch_id bigint,
    user_id bigint NOT NULL
);


ALTER TABLE finance_branch_employee OWNER TO postgres;

--
-- Name: finance_branch_employee_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE finance_branch_employee_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE finance_branch_employee_seq OWNER TO postgres;

--
-- Name: finance_branch_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE finance_branch_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE finance_branch_seq OWNER TO postgres;

--
-- Name: finance_details_entity; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE finance_details_entity (
    id bigint,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    city character varying(255),
    date_of_agreement bigint,
    finance_status integer,
    financer_m_id bigint,
    financer_mode integer,
    name character varying(255),
    district_entity bigint,
    mandal_entity bigint,
    state_entity bigint,
    vehicle_rc_id bigint,
    version integer
);


ALTER TABLE finance_details_entity OWNER TO postgres;

--
-- Name: finance_details_entity_backup; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE finance_details_entity_backup (
    id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    city character varying(255) NOT NULL,
    date_of_agreement bigint NOT NULL,
    finance_status integer,
    financer_m_id bigint,
    financer_mode integer NOT NULL,
    name character varying(255) NOT NULL,
    district_entity bigint NOT NULL,
    mandal_entity bigint NOT NULL,
    state_entity bigint NOT NULL,
    vehicle_rc_id bigint NOT NULL,
    version integer DEFAULT 0 NOT NULL
);


ALTER TABLE finance_details_entity_backup OWNER TO postgres;

--
-- Name: finance_details_entityn; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE finance_details_entityn (
    id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    city character varying(255) NOT NULL,
    date_of_agreement bigint NOT NULL,
    finance_status integer,
    financer_m_id bigint,
    financer_mode integer NOT NULL,
    name character varying(255) NOT NULL,
    district_entity bigint NOT NULL,
    mandal_entity bigint NOT NULL,
    state_entity bigint NOT NULL,
    vehicle_rc_id bigint NOT NULL,
    version integer DEFAULT 0 NOT NULL
);


ALTER TABLE finance_details_entityn OWNER TO postgres;

--
-- Name: finance_history; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE finance_history (
    id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    attachments character varying(255),
    comments character varying(255),
    status integer,
    finance_id bigint,
    service_type integer,
    token_id character varying(255),
    vehicle_rc_id bigint NOT NULL
);


ALTER TABLE finance_history OWNER TO postgres;

--
-- Name: finance_token; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE finance_token (
    id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    app_status integer,
    date_of_generation bigint,
    quotation_price double precision,
    requester_type integer NOT NULL,
    service_type integer,
    token_id character varying(255),
    vehicle_rc_id bigint NOT NULL
);


ALTER TABLE finance_token OWNER TO postgres;

--
-- Name: finance_token_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE finance_token_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE finance_token_seq OWNER TO postgres;

--
-- Name: finance_yard; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE finance_yard (
    id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    status integer,
    city character varying(255),
    finnace_branch bytea,
    name character varying(255),
    yard_address character varying(255),
    yard_area bigint,
    district_id bigint,
    user_id bigint,
    mandal_id bigint,
    state_id bigint
);


ALTER TABLE finance_yard OWNER TO postgres;

--
-- Name: finance_yard_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE finance_yard_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE finance_yard_seq OWNER TO postgres;

--
-- Name: financehistory_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE financehistory_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE financehistory_seq OWNER TO postgres;

--
-- Name: financer_app_status_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE financer_app_status_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE financer_app_status_seq OWNER TO postgres;

--
-- Name: financer_fresh_contact_details; Type: TABLE; Schema: public; Owner: rtamigprod; Tablespace: 
--

CREATE TABLE financer_fresh_contact_details (
    id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    email character varying(255),
    is_updated boolean DEFAULT false,
    mobile_number character varying(255),
    vehicle_rc_id bigint
);


ALTER TABLE financer_fresh_contact_details OWNER TO rtamigprod;

--
-- Name: financer_fresh_contact_details_seq; Type: SEQUENCE; Schema: public; Owner: rtamigprod
--

CREATE SEQUENCE financer_fresh_contact_details_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE financer_fresh_contact_details_seq OWNER TO rtamigprod;

--
-- Name: financer_fresh_rc; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE financer_fresh_rc (
    id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    amount_due bigint,
    cco_status integer,
    current_step integer,
    dtc_status integer,
    frf_number character varying(255),
    due_since bigint,
    owner_consent boolean,
    under_possession boolean,
    yard bigint,
    vehicle_rc_id bigint NOT NULL,
    application_number character varying,
    defaulted_amount bigint,
    no_of_emi bigint,
    show_cause_date bigint,
    owner_conscent_date bigint,
    owner_comment character varying,
    show_cause_issued_by character varying,
    show_cause_doc character varying
);


ALTER TABLE financer_fresh_rc OWNER TO postgres;

--
-- Name: financer_fresh_rc_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE financer_fresh_rc_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE financer_fresh_rc_seq OWNER TO postgres;

--
-- Name: financer_master; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE financer_master (
    id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    name character varying(255),
    user_id bigint,
    financier_pan_no character varying(255)
);


ALTER TABLE financer_master OWNER TO postgres;

--
-- Name: financer_seized_vehicles; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE financer_seized_vehicles (
    financer_siezed_vehicles_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    status integer,
    user_id bigint,
    vehicle_rc_id bigint NOT NULL
);


ALTER TABLE financer_seized_vehicles OWNER TO postgres;

--
-- Name: financer_seized_vehicles_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE financer_seized_vehicles_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE financer_seized_vehicles_seq OWNER TO postgres;

--
-- Name: fitness_cert_details; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE fitness_cert_details (
    fitness_id bigint NOT NULL,
    created_by character varying(50),
    created_on date,
    modified_by character varying(50),
    modified_on date,
    appl_id character varying(80),
    appl_origination character varying(20),
    appl_type character varying(80),
    applicant_id character varying(80),
    approvedby_mvi character varying(80),
    cfrr_count integer,
    cfrr_id bigint,
    cfrr_next_insp_dt timestamp without time zone,
    chasis_no character varying(50),
    engine_no character varying(25),
    expiry_date timestamp without time zone,
    fc_no character varying(80),
    fc_type character varying(1),
    fexm character varying(1),
    fexm_reason character varying(200),
    ghat_road_flag boolean,
    issue_date timestamp without time zone,
    module_code character varying(20),
    remarks character varying(255),
    status_code integer,
    support_ticket_details character varying(2000),
    validity_flag character varying(1),
    vechicle_class character varying(10),
    vehicle_rc_id bigint,
    vehicle_regd_no character varying(255),
    insp_office_code bigint,
    rta_office_code bigint
);


ALTER TABLE fitness_cert_details OWNER TO postgres;

--
-- Name: fitness_cert_details_history; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE fitness_cert_details_history (
    fitness_history_id bigint NOT NULL,
    created_by character varying(50),
    created_on date,
    modified_by character varying(50),
    modified_on date,
    appl_id character varying(255),
    appl_origination character varying(255),
    appl_type character varying(255),
    applicant_id character varying(255),
    approvedby_mvi character varying(255),
    cfrr_count integer,
    cfrr_id bigint,
    cfrr_next_insp_dt timestamp without time zone,
    chasis_no character varying(255),
    engine_no character varying(255),
    expiry_date timestamp without time zone,
    fc_no character varying(255),
    fc_type character varying(255),
    fexm character varying(255),
    fexm_reason character varying(255),
    fitness_id bigint,
    ghat_road_flag boolean,
    issue_date timestamp without time zone,
    module_code character varying(255),
    remarks character varying(255),
    status_code integer,
    support_ticket_details character varying(255),
    validity_flag character varying(255),
    vehicle_rc_id bigint,
    vehicle_regd_no character varying(255),
    insp_office_code bigint,
    rta_office_code bigint
);


ALTER TABLE fitness_cert_details_history OWNER TO postgres;

--
-- Name: fitness_fee_detail; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE fitness_fee_detail (
    fitness_fee_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    fitness_fee double precision,
    fitness_service double precision,
    total_fitness_fee double precision,
    vehicle_rc_id bigint
);


ALTER TABLE fitness_fee_detail OWNER TO postgres;

--
-- Name: fitness_fee_detail_history; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE fitness_fee_detail_history (
    fitness_fee_history_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    fitness_fee double precision,
    fitness_service double precision,
    total_fitness_fee double precision,
    vehicle_rc_id bigint
);


ALTER TABLE fitness_fee_detail_history OWNER TO postgres;

--
-- Name: fitness_fee_details; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE fitness_fee_details (
    fitness_fee_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    fitness_fee double precision,
    fitness_service double precision,
    reg_type integer,
    total_fitness_fee double precision,
    transaction_id bigint,
    version integer DEFAULT 0 NOT NULL,
    vehicle_rc_id bigint NOT NULL
);


ALTER TABLE fitness_fee_details OWNER TO postgres;

--
-- Name: fitness_fee_history_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE fitness_fee_history_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE fitness_fee_history_seq OWNER TO postgres;

--
-- Name: fitness_fee_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE fitness_fee_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE fitness_fee_seq OWNER TO postgres;

--
-- Name: fitness_history_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE fitness_history_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE fitness_history_seq OWNER TO postgres;

--
-- Name: fitness_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE fitness_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE fitness_seq OWNER TO postgres;

--
-- Name: green_dtl_hist_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE green_dtl_hist_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE green_dtl_hist_seq OWNER TO postgres;

--
-- Name: green_tax_details; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE green_tax_details (
    green_tax_dtl_id bigint NOT NULL,
    green_tax_amt double precision NOT NULL,
    green_tax_valid_to bigint,
    vehicle_rc_id bigint NOT NULL,
    transaction_id bigint,
    reg_type integer,
    version integer DEFAULT 0 NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    vehicle_cov character varying(50),
    exemption_flag boolean,
    remarks character varying(255),
    status integer,
    rta_office_code character varying(50)
);


ALTER TABLE green_tax_details OWNER TO postgres;

--
-- Name: green_tax_details_history; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE green_tax_details_history (
    green_dtl_hist_id bigint NOT NULL,
    green_tax_amt double precision NOT NULL,
    green_tax_valid_to bigint,
    vehicle_rc_id bigint NOT NULL,
    transaction_id bigint,
    reg_type integer,
    version integer DEFAULT 0 NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    vehicle_cov character varying(50),
    rta_office_code character varying(50),
    exemption_flag boolean,
    remarks character varying(255),
    status integer
);


ALTER TABLE green_tax_details_history OWNER TO postgres;

--
-- Name: green_tax_seq; Type: SEQUENCE; Schema: public; Owner: rtamigprod
--

CREATE SEQUENCE green_tax_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE green_tax_seq OWNER TO rtamigprod;

--
-- Name: hazardous_vehicle_driving_institute_users; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE hazardous_vehicle_driving_institute_users (
    hazardous_vehicle_driving_institute_users_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    name character varying(255),
    user_id bigint
);


ALTER TABLE hazardous_vehicle_driving_institute_users OWNER TO postgres;

--
-- Name: hazardous_vehicle_driving_institute_users_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE hazardous_vehicle_driving_institute_users_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hazardous_vehicle_driving_institute_users_seq OWNER TO postgres;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hibernate_sequence OWNER TO postgres;

--
-- Name: home_tax; Type: TABLE; Schema: public; Owner: rtamigprod; Tablespace: 
--

CREATE TABLE home_tax (
    home_tax_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    add_charge double precision DEFAULT 0,
    area_route character varying(50),
    from_age double precision DEFAULT 0,
    from_cc integer DEFAULT 0,
    from_date timestamp without time zone,
    from_invoice_amt double precision DEFAULT 0,
    from_km integer DEFAULT 0,
    from_rlw integer DEFAULT 0,
    from_seat integer DEFAULT 0,
    from_ulw integer DEFAULT 0,
    inc_amt double precision DEFAULT 0,
    inc_rlw integer DEFAULT 0,
    ownership_type character varying(50),
    permit_code character varying(50),
    permit_sub_type character varying(50),
    seat_type character(1),
    state_code character varying(50),
    tax_amt double precision DEFAULT 0,
    tax_percent double precision DEFAULT 0,
    tax_type character varying(50),
    to_age double precision DEFAULT 0,
    to_cc integer DEFAULT 0,
    to_date timestamp without time zone,
    to_invoice_amt double precision DEFAULT 0,
    to_km integer DEFAULT 0,
    to_rlw integer DEFAULT 0,
    to_seat integer DEFAULT 0,
    to_ulw integer DEFAULT 0,
    vehicle_sub_class character varying(50)
);


ALTER TABLE home_tax OWNER TO rtamigprod;

--
-- Name: home_tax_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE home_tax_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE home_tax_seq OWNER TO postgres;

--
-- Name: hsrp_detail; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE hsrp_detail (
    hsrp_detail_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    affixation_center_code character varying(255),
    affixation_date bigint,
    amount double precision,
    auth_ref_no character varying(255),
    embossing_date bigint,
    front_laser_code character varying(255),
    hsrp_availability_date bigint,
    hsrp_status integer,
    order_date bigint,
    order_no character varying(255),
    payment_recieve_date bigint,
    rear_laser_code character varying(255),
    rta_status integer,
    status integer,
    transaction_no character varying(255),
    transaction_status character varying(255),
    hsrp_transaction_id bigint,
    vehicle_rc_id bigint NOT NULL,
    iteration integer,
    message character varying(255)
);


ALTER TABLE hsrp_detail OWNER TO postgres;

--
-- Name: hsrp_detail_hist_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE hsrp_detail_hist_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hsrp_detail_hist_seq OWNER TO postgres;

--
-- Name: hsrp_detail_history; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE hsrp_detail_history (
    hsrp_detail_hist_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    amount double precision,
    auth_ref_no character varying(255),
    order_no character varying(255),
    request_parameter character varying(255),
    response_parameter character varying(255),
    status integer,
    transaction_no character varying(255),
    transaction_status character varying(255),
    hsrp_detail_id bigint,
    vehicle_rc_id bigint NOT NULL
);


ALTER TABLE hsrp_detail_history OWNER TO postgres;

--
-- Name: hsrp_detail_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE hsrp_detail_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hsrp_detail_seq OWNER TO postgres;

--
-- Name: hsrp_fee_details; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE hsrp_fee_details (
    hsrp_fee_dtl_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    hsrp_fee double precision NOT NULL,
    reg_type integer,
    transaction_id bigint,
    version integer DEFAULT 0 NOT NULL,
    vehicle_rc_id bigint NOT NULL
);


ALTER TABLE hsrp_fee_details OWNER TO postgres;

--
-- Name: hsrp_fee_details_history; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE hsrp_fee_details_history (
    hsrp_hist_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    hsrp_fee double precision NOT NULL,
    reg_type integer,
    transaction_id bigint,
    version integer DEFAULT 0 NOT NULL,
    vehicle_rc_id bigint NOT NULL
);


ALTER TABLE hsrp_fee_details_history OWNER TO postgres;

--
-- Name: hsrp_fee_dtl_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE hsrp_fee_dtl_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hsrp_fee_dtl_seq OWNER TO postgres;

--
-- Name: hsrp_hist_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE hsrp_hist_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hsrp_hist_seq OWNER TO postgres;

--
-- Name: hsrp_trans_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE hsrp_trans_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hsrp_trans_seq OWNER TO postgres;

--
-- Name: hsrp_transaction; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE hsrp_transaction (
    hsrp_transaction_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    amount_7 double precision,
    authorization_code character varying(255),
    credit_account_7 character varying(255),
    other_charges_description_2 character varying(255),
    other_charges_tid_2 character varying(255),
    status character varying(255),
    transaction_detail bigint NOT NULL,
    vehicle_rc_id bigint NOT NULL,
    registration_type integer
);


ALTER TABLE hsrp_transaction OWNER TO postgres;

--
-- Name: insurance_details; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE insurance_details (
    insurance_dtl_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    status integer NOT NULL,
    end_date bigint NOT NULL,
    mode character varying(255) NOT NULL,
    policy_no character varying(255) NOT NULL,
    provider character varying(255) NOT NULL,
    start_date bigint NOT NULL,
    tenure integer NOT NULL,
    insurance_id bigint NOT NULL,
    vehicle_dtl_id bigint NOT NULL,
    vehicle_rc_id bigint NOT NULL,
    version integer DEFAULT 0 NOT NULL
);


ALTER TABLE insurance_details OWNER TO postgres;

--
-- Name: insurance_dtl_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE insurance_dtl_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE insurance_dtl_seq OWNER TO postgres;

--
-- Name: insurance_type; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE insurance_type (
    insurance_type_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    name character varying(255),
    status boolean,
    code character varying(50)
);


ALTER TABLE insurance_type OWNER TO postgres;

--
-- Name: insurance_type_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE insurance_type_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE insurance_type_seq OWNER TO postgres;

--
-- Name: late_fee_details; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE late_fee_details (
    late_fee_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    late_fee_amt double precision,
    late_fee_type integer,
    reg_type integer,
    transaction_id bigint,
    version integer DEFAULT 0 NOT NULL,
    vehicle_rc_id bigint NOT NULL
);


ALTER TABLE late_fee_details OWNER TO postgres;

--
-- Name: late_fee_details_history; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE late_fee_details_history (
    late_fee_hist_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    late_fee_type integer,
    late_fee_amt double precision,
    reg_type integer,
    status integer,
    transaction_id bigint,
    version integer DEFAULT 0 NOT NULL,
    vehicle_rc_id bigint
);


ALTER TABLE late_fee_details_history OWNER TO postgres;

--
-- Name: late_fee_details_history_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE late_fee_details_history_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE late_fee_details_history_seq OWNER TO postgres;

--
-- Name: late_fee_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE late_fee_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE late_fee_seq OWNER TO postgres;

--
-- Name: learners_permit_dtls; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE learners_permit_dtls (
    licence_holder_id bigint NOT NULL,
    llr_sequence_id integer NOT NULL,
    llr_vehicle_class_code character varying(255) NOT NULL,
    created_by character varying(50),
    created_on date,
    modified_by character varying(50),
    modified_on date,
    application_id character varying(255),
    application_origination character varying(255),
    date_of_first_issue date,
    llr_issuedt date,
    llr_no character varying(255),
    llr_no_type character varying(255),
    medical_fitness_type character varying(255),
    medical_practioner_code character varying(255),
    parent_consent_aadhaar_no character varying(255),
    photo_attachment_id bigint,
    reference_id character varying(255),
    retest_flag character varying(255),
    sign_attachment_id bigint,
    status_code character varying(255),
    status_date date,
    status_remarks character varying(255),
    status_updated_by character varying(255),
    test_date date,
    test_exempted character(1),
    test_exempted_reason character varying(255),
    test_id character varying(255),
    test_result character varying(255),
    ticket_details character varying(255),
    valid_from date,
    valid_to date,
    ao_user_id bigint,
    mvi_user_id bigint,
    rta_office_code bigint
);


ALTER TABLE learners_permit_dtls OWNER TO postgres;

--
-- Name: learners_permit_dtls_history; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE learners_permit_dtls_history (
    learners_permit_hist_id bigint NOT NULL,
    created_by character varying(50),
    created_on date,
    modified_by character varying(50),
    modified_on date,
    application_id character varying(255),
    application_origination character varying(255),
    approved_ao character varying(255),
    approved_mvi character varying(255),
    date_of_first_issue date,
    licence_holder_id bigint,
    llr_issuedt date,
    llr_no character varying(255),
    llr_no_type character varying(255),
    llr_sequence_id integer,
    llr_vehicle_class_code character varying(255),
    medical_fitness_type character varying(255),
    medical_practioner_code character varying(255),
    parent_consent_aadhaar_no character varying(255),
    photo_attachment_id bigint,
    reference_id character varying(255),
    retest_flag character varying(255),
    service_code character varying(255),
    sign_attachment_id bigint,
    status_code character varying(255),
    status_date date,
    status_remarks character varying(255),
    status_updated_by character varying(255),
    test_date date,
    test_exempted character(1),
    test_exempted_reason character varying(255),
    test_id character varying(255),
    test_result character varying(255),
    ticket_details character varying(255),
    valid_from date,
    valid_to date,
    rta_office_code bigint
);


ALTER TABLE learners_permit_dtls_history OWNER TO postgres;

--
-- Name: learners_permit_hist_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE learners_permit_hist_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE learners_permit_hist_seq OWNER TO postgres;

--
-- Name: legal_hier_details; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE legal_hier_details (
    legal_hier_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    application_id bigint,
    hier_consent boolean,
    legal_hier_aadhar_number character varying(255),
    pr_number character varying(255),
    vehicle_rc_id bigint
);


ALTER TABLE legal_hier_details OWNER TO postgres;

--
-- Name: legal_hier_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE legal_hier_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE legal_hier_seq OWNER TO postgres;

--
-- Name: licence_attachment_details; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE licence_attachment_details (
    attachment_detail_id bigint NOT NULL,
    created_by character varying(50),
    created_on date,
    modified_by character varying(50),
    modified_on date,
    attachment_from integer NOT NULL,
    attachment_title character varying(200) NOT NULL,
    file_name character varying(200) NOT NULL,
    source character varying(255) NOT NULL,
    status integer NOT NULL,
    user_role integer,
    doc_master_id integer NOT NULL,
    licence_holder_id bigint NOT NULL
);


ALTER TABLE licence_attachment_details OWNER TO postgres;

--
-- Name: licence_attachment_details_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE licence_attachment_details_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE licence_attachment_details_sequence OWNER TO postgres;

--
-- Name: licence_holder_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE licence_holder_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE licence_holder_seq OWNER TO postgres;

--
-- Name: licence_print_request; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE licence_print_request (
    print_request_id bigint NOT NULL,
    created_by character varying(50),
    created_on date,
    modified_by character varying(50),
    modified_on date,
    acknowledge_date date,
    application_id character varying(255),
    dispatch_date date,
    dispatch_tracking_no character varying(255),
    dispatched_by character varying(255),
    duplicate character varying(255),
    licence_holder_id bigint,
    permit_type character varying(255),
    print_date date,
    rc_card_employee_id character varying(255),
    reprint_reason character varying(255),
    request_date date,
    requested_by character varying(255),
    status character varying(255)
);


ALTER TABLE licence_print_request OWNER TO postgres;

--
-- Name: license_holder_dtls; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE license_holder_dtls (
    licence_holder_id bigint NOT NULL,
    created_by character varying(50),
    created_on date,
    modified_by character varying(50),
    modified_on date,
    aadhaar_no character varying(255),
    blood_donor character varying(255),
    blood_grp character varying(255),
    cfst_applicant_id character varying(255),
    date_of_birth date,
    display_name character varying(255),
    electoral_number character varying(255),
    email character varying(255),
    first_name character varying(255),
    firstaidcertified character varying(255),
    foreign_military character varying(255),
    gender character varying(255),
    guardian_name character varying(255),
    handicap_dtls character varying(255),
    is_active character varying(255),
    is_adhar_verify character varying(255),
    is_handicapped character varying(255),
    is_same_as_aadhaar boolean DEFAULT false,
    last_name character varying(255),
    mobile_no character varying(255),
    nationality character varying(255),
    organ_donor character varying(255),
    otherstate_cd character varying(255),
    pan_no character varying(255),
    perm_addr_country character varying(255),
    perm_addr_district character varying(255),
    perm_addr_door_no character varying(255),
    perm_addr_mandal character varying(255),
    perm_addr_pin_code character varying(255),
    perm_addr_state character varying(255),
    perm_addr_street character varying(255),
    perm_addr_town character varying(255),
    pres_addr_country_id bigint,
    pres_addr_door_no character varying(255),
    pres_addr_pin_code character varying(255),
    pres_addr_street character varying(255),
    pres_addr_town character varying(255),
    ticket_details character varying(255),
    twotire character varying(255),
    licence_identity_id bigint,
    pres_addr_district_id bigint,
    pres_addr_mandal_id bigint,
    pres_addr_state_id bigint,
    qualification_id bigint,
    rta_office_code bigint
);


ALTER TABLE license_holder_dtls OWNER TO postgres;

--
-- Name: license_holder_txns; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE license_holder_txns (
    licence_holder_id bigint NOT NULL,
    sequence_id integer NOT NULL,
    created_by character varying(50),
    created_on date,
    modified_by character varying(50),
    modified_on date,
    aadhaar_no character varying(255),
    application_id character varying(255),
    blood_donor character varying(255),
    blood_grp character varying(255),
    cfst_applicant_id character varying(255),
    date_of_birth date,
    display_name character varying(255),
    electoral_number character varying(255),
    email character varying(255),
    first_name character varying(255),
    firstaidcertified character varying(255),
    foreign_military character varying(255),
    gender character varying(255),
    guardian_name character varying(255),
    handicap_dtls character varying(255),
    is_active character varying(255),
    is_adhar_verify character varying(255),
    is_handicapped character varying(255),
    last_name character varying(255),
    mobile_no character varying(255),
    nationality character varying(255),
    old_sequence_id integer,
    old_value character varying(255),
    organ_donor character varying(255),
    otherstate_cd character varying(255),
    pan_no character varying(255),
    passport_no character varying(255),
    passport_valid_to date,
    perm_addr_country character varying(255),
    perm_addr_district character varying(255),
    perm_addr_door_no character varying(255),
    perm_addr_mandal character varying(255),
    perm_addr_pin_code character varying(255),
    perm_addr_state character varying(255),
    perm_addr_street character varying(255),
    perm_addr_town character varying(255),
    pres_addr_country_id character varying(255),
    pres_addr_door_no character varying(255),
    pres_addr_pin_code character varying(255),
    pres_addr_street character varying(255),
    pres_addr_town character varying(255),
    reference_number character varying(255),
    ticket_details character varying(255),
    transaction_reason character varying(255),
    transaction_status character varying(255),
    transaction_type character varying(255),
    pres_addr_district_id bigint,
    pres_addr_mandal_id bigint,
    pres_addr_state_id bigint,
    qualification_id bigint,
    rta_office_code bigint
);


ALTER TABLE license_holder_txns OWNER TO postgres;

--
-- Name: license_identities_details; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE license_identities_details (
    license_identity_id bigint NOT NULL,
    created_by character varying(50),
    created_on date,
    modified_by character varying(50),
    modified_on date,
    dl_number character varying(255),
    dl_valid_from date,
    dl_valid_to date,
    passport_number character varying(255),
    passport_valid_from date,
    passport_valid_to date,
    visa_valid_from date,
    visa_valid_to date,
    rta_office_address_id bigint
);


ALTER TABLE license_identities_details OWNER TO postgres;

--
-- Name: license_identities_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE license_identities_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE license_identities_seq OWNER TO postgres;

--
-- Name: license_idp_dtls; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE license_idp_dtls (
    idp_sequence_id integer NOT NULL,
    licence_holder_id bigint NOT NULL,
    created_by character varying(50),
    created_on date,
    modified_by character varying(50),
    modified_on date,
    application_no character varying(255),
    approved_by character varying(255),
    idp_license_no character varying(255),
    idp_vehicle_class_code character varying(255),
    issue_date date,
    passport_no character varying(255),
    passport_valid_to date,
    photo_attachment_id bigint,
    status integer,
    status_remarks character varying(255),
    ticket_details character varying(255),
    valid_from date,
    valid_to date,
    rta_office_code bigint
);


ALTER TABLE license_idp_dtls OWNER TO postgres;

--
-- Name: license_vehicle_class_ref; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE license_vehicle_class_ref (
    vehicle_class character varying(50) NOT NULL,
    created_by character varying(50),
    created_on date,
    modified_by character varying(50),
    modified_on date,
    age_group_cd character varying(255),
    badge_available boolean,
    hazardous boolean,
    idp_class character varying(50),
    licence_class_type character varying(255),
    max_age integer,
    requires_doctor_cert boolean,
    validity_period integer
);


ALTER TABLE license_vehicle_class_ref OWNER TO postgres;

--
-- Name: life_tax_details; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE life_tax_details (
    life_tax_dtl_id bigint NOT NULL,
    tax_amt double precision NOT NULL,
    vehicle_rc_id bigint NOT NULL,
    tax_valid_upto bigint,
    tax_valid_from bigint,
    second_vehicle_tax_percent double precision,
    second_vehicle_tax_amt double precision,
    second_vehicle_tax_on bigint,
    total_tax double precision NOT NULL,
    transaction_id bigint,
    reg_type integer,
    status integer,
    exemption_flag boolean,
    remarks character varying(255),
    rta_office_code character varying(50),
    vehicle_cov character varying(50),
    version integer DEFAULT 0 NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    tax_percent double precision
);


ALTER TABLE life_tax_details OWNER TO postgres;

--
-- Name: life_tax_details_dup; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE life_tax_details_dup (
    life_tax_dtl_id bigint NOT NULL,
    tax_amt double precision NOT NULL,
    vehicle_rc_id bigint NOT NULL,
    tax_valid_upto bigint,
    tax_valid_from bigint,
    second_vehicle_tax_percent double precision,
    second_vehicle_tax_amt double precision,
    second_vehicle_tax_on bigint,
    total_tax double precision NOT NULL,
    transaction_id bigint,
    reg_type integer,
    status integer,
    exemption_flag boolean,
    remarks character varying(255),
    rta_office_code character varying(50),
    vehicle_cov character varying(50),
    version integer DEFAULT 0,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    tax_percent double precision
);


ALTER TABLE life_tax_details_dup OWNER TO postgres;

--
-- Name: life_tax_details_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE life_tax_details_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE life_tax_details_seq OWNER TO postgres;

--
-- Name: lifetaxupd; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE lifetaxupd (
    vehicle_rc_id bigint,
    tax_amt double precision
);


ALTER TABLE lifetaxupd OWNER TO postgres;

--
-- Name: login_track; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE login_track (
    login_track_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    ip_address character varying(255),
    login_time bigint,
    user_id bigint
);


ALTER TABLE login_track OWNER TO postgres;

--
-- Name: login_track_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE login_track_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE login_track_seq OWNER TO postgres;

--
-- Name: maker_master; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE maker_master (
    maker_id integer NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    maker_name character varying(200) NOT NULL,
    status boolean NOT NULL
);


ALTER TABLE maker_master OWNER TO postgres;

--
-- Name: maker_master_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE maker_master_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE maker_master_sequence OWNER TO postgres;

--
-- Name: mandal; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE mandal (
    mandal_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    name character varying(255),
    status boolean,
    code integer,
    district_id bigint
);


ALTER TABLE mandal OWNER TO postgres;

--
-- Name: mandal_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE mandal_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mandal_id_seq OWNER TO postgres;

--
-- Name: medical_practitioner_user_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE medical_practitioner_user_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE medical_practitioner_user_seq OWNER TO postgres;

--
-- Name: medical_practitioner_users; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE medical_practitioner_users (
    medical_practitioner_user_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    medical_license_number character varying(255),
    name character varying(255),
    user_id bigint
);


ALTER TABLE medical_practitioner_users OWNER TO postgres;

--
-- Name: model_details; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE model_details (
    model_details_id integer NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    model_name character varying(200) NOT NULL,
    status boolean NOT NULL,
    maker_master_id integer NOT NULL
);


ALTER TABLE model_details OWNER TO postgres;

--
-- Name: model_details_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE model_details_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE model_details_sequence OWNER TO postgres;

--
-- Name: neigh_district_mapping; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE neigh_district_mapping (
    district_id bigint NOT NULL,
    neigh_district_id bigint
);


ALTER TABLE neigh_district_mapping OWNER TO postgres;

--
-- Name: neigh_district_master; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE neigh_district_master (
    neigh_dist_id bigint NOT NULL,
    created_by character varying(80),
    created_on timestamp without time zone,
    dist_id bigint,
    modified_by character varying(80),
    modified_on timestamp without time zone
);


ALTER TABLE neigh_district_master OWNER TO postgres;

--
-- Name: neigh_state_master; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE neigh_state_master (
    neigh_state_id bigint NOT NULL,
    created_by character varying(80),
    created_on timestamp without time zone,
    modified_by character varying(80),
    modified_on timestamp without time zone,
    state_id bigint
);


ALTER TABLE neigh_state_master OWNER TO postgres;

--
-- Name: new_vehicle_rc_migration; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE new_vehicle_rc_migration (
    vehicle_rc_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    ao_action_status integer,
    ao_user_id bigint,
    cco_action_status integer,
    cco_user_id bigint,
    dealer_action_status integer,
    dealer_id bigint,
    mvi_action_status integer,
    mvi_user_id bigint,
    rto_action_status integer,
    rto_user_id bigint
);


ALTER TABLE new_vehicle_rc_migration OWNER TO postgres;

--
-- Name: newvechilepolicy; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE newvechilepolicy (
    ron_regn_no character varying(100),
    ron_chas_no character varying(100),
    ron_eng_no character varying(100),
    ron_ic_comp character varying(100),
    ron_ic_no character varying(100),
    ron_ic_from character varying(100),
    ron_ic_upto character varying(100),
    ron_iss_dt character varying(100),
    created_dt character varying(100),
    created_by character varying(100),
    modified_dt character varying(100),
    modified_by character varying(100)
);


ALTER TABLE newvechilepolicy OWNER TO postgres;

--
-- Name: noc_address_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE noc_address_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE noc_address_seq OWNER TO postgres;

--
-- Name: noc_details_history_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE noc_details_history_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE noc_details_history_seq OWNER TO postgres;

--
-- Name: noc_details_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE noc_details_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE noc_details_seq OWNER TO postgres;

--
-- Name: ownership_type; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE ownership_type (
    ownership_type_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    name character varying(255),
    status boolean,
    code character varying(50)
);


ALTER TABLE ownership_type OWNER TO postgres;

--
-- Name: ownership_type_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE ownership_type_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ownership_type_seq OWNER TO postgres;

--
-- Name: p_address_history_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE p_address_history_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE p_address_history_seq OWNER TO postgres;

--
-- Name: p_address_not_matched; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE p_address_not_matched (
    p_address_not_matched_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    aadhar_district character varying(255),
    aadhar_mandal character varying(255),
    aadhar_no character varying(255),
    aadhar_state character varying(255),
    customer_district character varying(255),
    customer_mandal character varying(255),
    customer_state character varying(255),
    vehicle_rc_id bigint,
    version integer DEFAULT 0 NOT NULL
);


ALTER TABLE p_address_not_matched OWNER TO postgres;

--
-- Name: p_address_not_matched_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE p_address_not_matched_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE p_address_not_matched_id_seq OWNER TO postgres;

--
-- Name: p_address_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE p_address_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE p_address_seq OWNER TO postgres;

--
-- Name: periodic_tax_details; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE periodic_tax_details (
    tax_dtl_id bigint NOT NULL,
    tax_type_id bigint NOT NULL,
    vehicle_rc_id bigint NOT NULL,
    tax_valid_upto bigint,
    tax_valid_from bigint,
    tax_amt double precision NOT NULL,
    tax_arrears double precision,
    penalty double precision,
    penalty_arrears double precision,
    service_charge double precision,
    vehicle_cov character varying(50),
    permit_type character varying(50),
    permit_sub_type character varying(50),
    total_amt double precision NOT NULL,
    transaction_id bigint,
    status integer,
    reg_type integer,
    exemption_flag boolean,
    remarks character varying(255),
    rta_office_code character varying(50),
    vehicle_reg_no character varying(50),
    version integer NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint
);


ALTER TABLE periodic_tax_details OWNER TO postgres;

--
-- Name: periodic_tax_details_dup; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE periodic_tax_details_dup (
    tax_dtl_id bigint NOT NULL,
    tax_type_id bigint NOT NULL,
    vehicle_rc_id bigint NOT NULL,
    tax_valid_upto bigint,
    tax_valid_from bigint NOT NULL,
    tax_amt double precision NOT NULL,
    tax_arrears double precision,
    penalty double precision,
    penalty_arrears double precision,
    service_charge double precision,
    vehicle_cov character varying(50),
    permit_type character varying(50),
    permit_sub_type character varying(50),
    total_amt double precision NOT NULL,
    transaction_id bigint,
    status integer,
    reg_type integer,
    exemption_flag boolean,
    remarks character varying(255),
    rta_office_code character varying(50),
    vehicle_reg_no character varying(50),
    version integer DEFAULT 0 NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint
);


ALTER TABLE periodic_tax_details_dup OWNER TO postgres;

--
-- Name: periodic_tax_details_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE periodic_tax_details_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE periodic_tax_details_seq OWNER TO postgres;

--
-- Name: permanent_address; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE permanent_address (
    p_address_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    city character varying(255),
    country character varying(255),
    district character varying(255),
    door_no character varying(255),
    mandal character varying(255),
    post_office character varying(255),
    state character varying(255),
    status boolean,
    street character varying(255),
    user_id bigint NOT NULL,
    user_type character varying(50),
    pin_code character varying(255),
    address_type integer DEFAULT 1,
    version integer DEFAULT 0 NOT NULL
);


ALTER TABLE permanent_address OWNER TO postgres;

--
-- Name: permanent_address_history; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE permanent_address_history (
    p_address_history_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    address_type integer DEFAULT 1,
    city character varying(255),
    country character varying(255),
    district character varying(255),
    door_no character varying(255),
    mandal character varying(255),
    p_address_id bigint,
    pin_code character varying(255),
    post_office character varying(255),
    service_code character varying(255),
    state character varying(255),
    status boolean,
    street character varying(255),
    user_id bigint NOT NULL,
    user_type character varying(50)
);


ALTER TABLE permanent_address_history OWNER TO postgres;

--
-- Name: permit_allowed; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE permit_allowed (
    permit_allowed_id bigint NOT NULL,
    allowed_count integer,
    current_count integer,
    rta_office_cd integer,
    state_id integer,
    year integer
);


ALTER TABLE permit_allowed OWNER TO postgres;

--
-- Name: permit_allowed_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE permit_allowed_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE permit_allowed_seq OWNER TO postgres;

--
-- Name: permit_auth_card_details; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE permit_auth_card_details (
    permit_auth_card_details_id bigint NOT NULL,
    appl_origination character varying(80),
    auth_card_id character varying(80),
    auth_card_issued_by character varying(80),
    auth_card_remarks character varying(255),
    auth_card_seq integer,
    auth_card_vfdt date,
    auth_card_vtdt date,
    created_by character varying(80),
    created_on timestamp without time zone,
    modified_by character varying(80),
    modified_on timestamp without time zone,
    permit_no character varying(80),
    rta_office_id bigint,
    status integer,
    support_ticket_remarks character varying(80)
);


ALTER TABLE permit_auth_card_details OWNER TO postgres;

--
-- Name: permit_auth_card_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE permit_auth_card_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE permit_auth_card_seq OWNER TO postgres;

--
-- Name: permit_conditions_master; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE permit_conditions_master (
    permit_cond_id integer NOT NULL,
    code character varying(50),
    created_by character varying(80),
    created_on timestamp without time zone,
    modified_by character varying(80),
    modified_on timestamp without time zone,
    permit_cond_desc character varying(800),
    permit_type character varying(50),
    rta_office_id integer,
    status_code integer,
    temp_permit_type_code character varying(50),
    vehicle_class_code character varying(80)
);


ALTER TABLE permit_conditions_master OWNER TO postgres;

--
-- Name: permit_conditions_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE permit_conditions_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE permit_conditions_seq OWNER TO postgres;

--
-- Name: permit_details_mapping; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE permit_details_mapping (
    permit_details_id bigint NOT NULL,
    created_by character varying(50),
    created_on timestamp without time zone,
    detail_type character varying(80),
    detail_value character varying(255),
    modified_by character varying(50),
    modified_on timestamp without time zone,
    permit_dtl_seq_id integer,
    permit_no character varying(80),
    permit_sequence_id integer,
    status integer
);


ALTER TABLE permit_details_mapping OWNER TO postgres;

--
-- Name: permit_details_mapping_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE permit_details_mapping_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE permit_details_mapping_seq OWNER TO postgres;

--
-- Name: permit_fee_detail; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE permit_fee_detail (
    permit_fee_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    other_permit_fee double precision,
    permit_fee double precision,
    permit_service double precision,
    permit_type_code character varying(255),
    status integer,
    total_permit_fee double precision,
    vehicle_rc_id bigint
);


ALTER TABLE permit_fee_detail OWNER TO postgres;

--
-- Name: permit_fee_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE permit_fee_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE permit_fee_seq OWNER TO postgres;

--
-- Name: permit_goods_master; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE permit_goods_master (
    permit_goods_id integer NOT NULL,
    code character varying(255),
    created_by character varying(80),
    created_on date,
    modified_by character varying(80),
    modified_on date,
    per_goods_desc character varying(80),
    permit_type character varying(255),
    remarks character varying(80),
    status boolean,
    temp_permit_type_code character varying(50),
    vehicle_class_code character varying(80)
);


ALTER TABLE permit_goods_master OWNER TO postgres;

--
-- Name: permit_goods_master_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE permit_goods_master_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE permit_goods_master_seq OWNER TO postgres;

--
-- Name: permit_header; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE permit_header (
    permit_header_id bigint NOT NULL,
    appl_id character varying(80),
    appl_origination character varying(80),
    appl_type character varying(20),
    approval_rmks character varying(255),
    approved_by character varying(80),
    approved_dt timestamp without time zone,
    created_by character varying(80),
    created_on timestamp without time zone,
    days_per_trip integer,
    entity_cd character varying(80),
    forward_journey_destination character varying(600),
    forward_journey_enroute character varying(600),
    forward_journey_starting_station character varying(600),
    hire_flag character varying(1),
    holder_aadhar_no character varying(80),
    holder_mobile_no character varying(12),
    holder_nm character varying(80),
    home_state_authority character varying(80),
    is_temp_permit boolean,
    issue_date timestamp without time zone,
    modified_by character varying(80),
    modified_on timestamp without time zone,
    module_cd character varying(80),
    no_of_trips integer,
    os_recom_lttr_id character varying(80),
    other_state_nm character varying(80),
    passanger_list_encl_id character varying(80),
    permit_issued_to character varying(80),
    permit_no character varying(80),
    permit_sequence_id integer,
    permit_sub_type character varying(255),
    permit_type character varying(255),
    ppd_kms numeric(7,2),
    ppd_plw numeric(8,2),
    purpose character varying(200),
    return_journey_destination character varying(600),
    return_journey_enroute character varying(600),
    return_journey_starting_station character varying(600),
    rta_office_id bigint,
    state_entry_dt timestamp without time zone,
    state_national_permit character varying(1),
    status integer,
    stu_rep_name character varying(80),
    support_ticket_remarks character varying(600),
    valid_from_date date,
    valid_to_date date,
    vehicle_class character varying(10),
    vehicle_regd_no character varying(80),
    detail_id bigint,
    off_cd character varying(15)
);


ALTER TABLE permit_header OWNER TO postgres;

--
-- Name: permit_header_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE permit_header_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE permit_header_seq OWNER TO postgres;

--
-- Name: permit_route_conditions_master; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE permit_route_conditions_master (
    permit_route_id bigint NOT NULL,
    code character varying(255),
    created_by character varying(80),
    created_on timestamp without time zone,
    modified_by character varying(80),
    modified_on timestamp without time zone,
    per_route_desc character varying(220),
    permit_type character varying(255),
    remarks character varying(80),
    status boolean,
    temp_permit_type_code character varying(50),
    vehicle_class_code character varying(80)
);


ALTER TABLE permit_route_conditions_master OWNER TO postgres;

--
-- Name: permit_route_master_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE permit_route_master_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE permit_route_master_seq OWNER TO postgres;

--
-- Name: permit_susp_dtls; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE permit_susp_dtls (
    permit_susp_dtls_id bigint NOT NULL,
    appl_origination character varying(80),
    application_id character varying(80),
    approved_auth character varying(80),
    cco_username character varying(80),
    created_by character varying(80),
    created_date timestamp without time zone,
    module_cd character varying(80),
    mvi_name character varying(80),
    offmemo_dt date,
    offmemo_no character varying(80),
    order_dt date,
    order_no character varying(80),
    permit_no character varying(80),
    permit_seq_id integer,
    planned_valid_from date,
    planned_valid_to date,
    punishment_desc character varying(200),
    reference_id character varying(80),
    revocation_dt date,
    revoke_reason character varying(200),
    rta_office_code integer,
    sc_issued character varying(1),
    section_rule character varying(80),
    status_code character varying(1),
    status_date timestamp without time zone,
    status_remarks character varying(200),
    status_updated_by character varying(80),
    status_updated_on timestamp without time zone,
    status_valid_from date,
    status_valid_to date,
    support_ticket_remarks character varying(80),
    sus_can character varying(1),
    ticket_details character varying(80),
    updated_by character varying(80),
    updated_date timestamp without time zone,
    vehicle_regd_no character varying(80)
);


ALTER TABLE permit_susp_dtls OWNER TO postgres;

--
-- Name: permit_susp_dtls_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE permit_susp_dtls_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE permit_susp_dtls_seq OWNER TO postgres;

--
-- Name: permit_type_master; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE permit_type_master (
    permit_type_id integer NOT NULL,
    auth_card_req boolean,
    code character varying(80),
    created_by character varying(80),
    created_on timestamp without time zone,
    is_temp_permit boolean,
    modified_by character varying(80),
    modified_on timestamp without time zone,
    name character varying(255),
    status boolean,
    validity_period integer
);


ALTER TABLE permit_type_master OWNER TO postgres;

--
-- Name: permit_type_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE permit_type_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE permit_type_sequence OWNER TO postgres;

--
-- Name: permit_vclass_mapping_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE permit_vclass_mapping_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE permit_vclass_mapping_seq OWNER TO postgres;

--
-- Name: permit_vehicle_class_mapping; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE permit_vehicle_class_mapping (
    permit_vclass_mapping_id bigint NOT NULL,
    created_by character varying(80),
    created_on timestamp without time zone,
    is_temp_permit_allowed boolean,
    modified_by character varying(80),
    modified_on timestamp without time zone,
    status integer,
    vehicle_class_code character varying(80),
    permit_type character varying(80) NOT NULL
);


ALTER TABLE permit_vehicle_class_mapping OWNER TO postgres;

--
-- Name: post_office; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE post_office (
    post_office_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    name character varying(255),
    status boolean,
    pin_code character varying(6)
);


ALTER TABLE post_office OWNER TO postgres;

--
-- Name: post_office_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE post_office_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE post_office_id_seq OWNER TO postgres;

--
-- Name: pr_mapping; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE pr_mapping (
    pr_number character varying(255),
    prod_rc_id bigint,
    mig_rc_id bigint
);


ALTER TABLE pr_mapping OWNER TO postgres;

--
-- Name: pr_series_master; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE pr_series_master (
    pr_series_master_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    end_number integer,
    series character varying(255),
    start_number integer,
    status integer,
    use_number integer,
    rta_office_id bigint,
    version integer DEFAULT 0 NOT NULL,
    reg_type integer
);


ALTER TABLE pr_series_master OWNER TO postgres;

--
-- Name: pr_series_master_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE pr_series_master_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE pr_series_master_seq OWNER TO postgres;

--
-- Name: primary_temp_permit_mapping; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE primary_temp_permit_mapping (
    primary_temp_permit_mapping_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    primary_permit_code character varying(255),
    temporary_permit_code character varying(255)
);


ALTER TABLE primary_temp_permit_mapping OWNER TO postgres;

--
-- Name: primary_temp_permit_mapping_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE primary_temp_permit_mapping_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE primary_temp_permit_mapping_seq OWNER TO postgres;

--
-- Name: print_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE print_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE print_seq OWNER TO postgres;

--
-- Name: puc_users; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE puc_users (
    puc_user_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    class_of_vehicle character varying(255),
    name character varying(255),
    user_id bigint
);


ALTER TABLE puc_users OWNER TO postgres;

--
-- Name: puc_users_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE puc_users_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE puc_users_seq OWNER TO postgres;

--
-- Name: qualification; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE qualification (
    qualification_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    name character varying(255),
    status boolean,
    code integer
);


ALTER TABLE qualification OWNER TO postgres;

--
-- Name: qualification_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE qualification_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE qualification_seq OWNER TO postgres;

--
-- Name: rc_card_employee; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE rc_card_employee (
    rccard_employee_id integer NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    rta_office_id bigint NOT NULL,
    user_id bigint NOT NULL
);


ALTER TABLE rc_card_employee OWNER TO postgres;

--
-- Name: rc_lock; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE rc_lock (
    rc_lock_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    cur_pr_status integer,
    lock_time bigint,
    pr_number character varying(255),
    pre_pr_status integer,
    vehicle_rc_id bigint
);


ALTER TABLE rc_lock OWNER TO postgres;

--
-- Name: rc_lock_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE rc_lock_seq
    START WITH 32
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE rc_lock_seq OWNER TO postgres;

--
-- Name: rccard_employee_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE rccard_employee_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE rccard_employee_sequence OWNER TO postgres;

--
-- Name: recom_lttr_issued_dtls; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE recom_lttr_issued_dtls (
    recom_lttr_issued_dtls_id character varying(255) NOT NULL,
    appl_origination character varying(80),
    created_by character varying(80),
    created_on timestamp without time zone,
    modified_by character varying(80),
    modified_on timestamp without time zone,
    permit_no character varying(80),
    permit_sequence_id integer,
    recom_issu_plc character varying(80),
    recom_issued_by character varying(80),
    recom_lttr_dt date,
    recom_lttr_id character varying(80),
    recom_lttr_vfdt date,
    recom_lttr_vtdt date,
    recom_state character varying(80),
    remarks character varying(80),
    status_code integer,
    support_ticket_remarks character varying(80)
);


ALTER TABLE recom_lttr_issued_dtls OWNER TO postgres;

--
-- Name: recom_lttr_issued_dtls_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE recom_lttr_issued_dtls_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE recom_lttr_issued_dtls_seq OWNER TO postgres;

--
-- Name: reg_fee_detail; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE reg_fee_detail (
    reg_fee_dtlid bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    imported_fee double precision,
    postal_charge double precision,
    reg_fee double precision NOT NULL,
    service_charge double precision,
    smart_card_fee double precision,
    special_number_fee double precision,
    total_reg_fee double precision NOT NULL,
    vehicle_rc_id bigint NOT NULL,
    fitness_fee bigint,
    permit_fee bigint,
    version integer DEFAULT 0 NOT NULL
);


ALTER TABLE reg_fee_detail OWNER TO postgres;

--
-- Name: reg_fee_detail_history; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE reg_fee_detail_history (
    reg_fee_hist_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    fitness_id bigint,
    imported_fee double precision,
    permit_id bigint,
    postal_charge double precision,
    reg_fee double precision,
    service_charge double precision,
    smart_card_fee double precision,
    special_number_fee double precision,
    total_reg_fee double precision,
    vehicle_rc_id bigint NOT NULL
);


ALTER TABLE reg_fee_detail_history OWNER TO postgres;

--
-- Name: reg_fee_details; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE reg_fee_details (
    reg_fee_dtl_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    hpa_fee double precision,
    imported_fee double precision,
    postal_charge double precision,
    pr_fee double precision,
    pr_service_charge double precision,
    reg_type integer,
    smart_card_fee double precision,
    special_number_fee double precision,
    total_fee double precision,
    tr_fee double precision,
    tr_service_charge double precision,
    transaction_id bigint,
    version integer DEFAULT 0 NOT NULL,
    vehicle_rc_id bigint NOT NULL
);


ALTER TABLE reg_fee_details OWNER TO postgres;

--
-- Name: reg_fee_dtl_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE reg_fee_dtl_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE reg_fee_dtl_seq OWNER TO postgres;

--
-- Name: reg_fee_hist_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE reg_fee_hist_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE reg_fee_hist_seq OWNER TO postgres;

--
-- Name: reg_fee_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE reg_fee_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE reg_fee_seq OWNER TO postgres;

--
-- Name: registration_category; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE registration_category (
    registration_category_id integer NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    name character varying(255),
    status boolean,
    code character varying(50)
);


ALTER TABLE registration_category OWNER TO postgres;

--
-- Name: registration_category_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE registration_category_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE registration_category_seq OWNER TO postgres;

--
-- Name: rejection_history; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE rejection_history (
    rejection_history_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    comment character varying(255),
    iteration integer,
    status integer,
    title character varying(255),
    user_type integer,
    attachment_details_id bigint,
    user_id bigint NOT NULL,
    vehicle_rc_id bigint NOT NULL
);


ALTER TABLE rejection_history OWNER TO postgres;

--
-- Name: rejection_history_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE rejection_history_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE rejection_history_sequence OWNER TO postgres;

--
-- Name: reset_password_log; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE reset_password_log (
    reset_pwd_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    new_password character varying(255),
    old_password character varying(255),
    user_id bigint
);


ALTER TABLE reset_password_log OWNER TO postgres;

--
-- Name: reset_pwd_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE reset_pwd_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE reset_pwd_seq OWNER TO postgres;

--
-- Name: role_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE role_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE role_seq OWNER TO postgres;

--
-- Name: route_details_master; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE route_details_master (
    stage_no bigint NOT NULL,
    created_by character varying(20),
    created_on timestamp without time zone,
    distance integer NOT NULL,
    modified_by character varying(20),
    modified_on timestamp without time zone,
    office_cd character varying(5) NOT NULL,
    route_serial integer NOT NULL,
    stage_name character varying(50) NOT NULL,
    status integer
);


ALTER TABLE route_details_master OWNER TO postgres;

--
-- Name: route_master; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE route_master (
    route_serial bigint NOT NULL,
    area_name character varying(150),
    area_type character varying(3),
    created_by character varying(20),
    created_on timestamp without time zone,
    dist_num integer,
    distance integer,
    end_point character varying(30),
    modified_by character varying(20),
    modified_on timestamp without time zone,
    office_cd character varying(5) NOT NULL,
    oper_type character varying(3),
    service_type character varying(3),
    start_point character varying(150),
    state_cd character varying(2) NOT NULL,
    status integer
);


ALTER TABLE route_master OWNER TO postgres;

--
-- Name: route_master_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE route_master_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE route_master_seq OWNER TO postgres;

--
-- Name: route_stage_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE route_stage_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE route_stage_seq OWNER TO postgres;

--
-- Name: rta_employee; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE rta_employee (
    rta_employee_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    dept_name character varying(255),
    email_id character varying(255),
    fax character varying(255),
    mobile character varying(255),
    phone character varying(255),
    employee_sign_file_name character varying(255),
    status boolean,
    rta_office_id bigint NOT NULL,
    user_id bigint NOT NULL
);


ALTER TABLE rta_employee OWNER TO postgres;

--
-- Name: rta_employee_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE rta_employee_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE rta_employee_seq OWNER TO postgres;

--
-- Name: rta_financedetails_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE rta_financedetails_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE rta_financedetails_seq OWNER TO postgres;

--
-- Name: rta_financer_master_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE rta_financer_master_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE rta_financer_master_seq OWNER TO postgres;

--
-- Name: rta_mandal_mapping; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE rta_mandal_mapping (
    rta_office_id bigint,
    mandal_id bigint NOT NULL
);


ALTER TABLE rta_mandal_mapping OWNER TO postgres;

--
-- Name: rta_office; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE rta_office (
    rta_office_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    code character varying(255),
    district_code character varying(255),
    email character varying(255),
    fax character varying(255),
    mobile character varying(255),
    name character varying(255),
    office_type character varying(255),
    parent_office_code character varying(255),
    phone character varying(255),
    series_map_id character varying(255),
    status boolean
);


ALTER TABLE rta_office OWNER TO postgres;

--
-- Name: rta_office_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE rta_office_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE rta_office_seq OWNER TO postgres;

--
-- Name: rta_offices_serial_no; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE rta_offices_serial_no (
    rta_offices_serial_no_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    rta_office_code character varying(255),
    serial_no bigint,
    type character varying(255),
    version integer DEFAULT 0 NOT NULL,
    year integer
);


ALTER TABLE rta_offices_serial_no OWNER TO postgres;

--
-- Name: rta_offices_serial_no_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE rta_offices_serial_no_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE rta_offices_serial_no_seq OWNER TO postgres;

--
-- Name: rta_service_charge; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE rta_service_charge (
    rta_service_charge_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    module_code character varying(255),
    amount double precision,
    transaction_id character varying(255),
    vehicle_code character varying(255),
    status integer
);


ALTER TABLE rta_service_charge OWNER TO postgres;

--
-- Name: rta_service_charge_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE rta_service_charge_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE rta_service_charge_id_seq OWNER TO postgres;

--
-- Name: rta_vehicle_tax; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE rta_vehicle_tax (
    rta_vehicle_tax_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    fr_ulw double precision,
    ownership_type character varying(255),
    state character varying(255),
    tax_amount double precision,
    tax_type character varying(255),
    to_date character varying(255),
    to_ulw double precision,
    vehicle_code character varying(255),
    status integer
);


ALTER TABLE rta_vehicle_tax OWNER TO postgres;

--
-- Name: rta_vehicle_tax_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE rta_vehicle_tax_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE rta_vehicle_tax_seq OWNER TO postgres;

--
-- Name: sbi_ddo; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE sbi_ddo (
    sbi_ddo_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    ddo_code character varying(255),
    district_code character varying(255),
    district_name character varying(255),
    treasury_name character varying(255)
);


ALTER TABLE sbi_ddo OWNER TO postgres;

--
-- Name: sbi_ddo_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE sbi_ddo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sbi_ddo_seq OWNER TO postgres;

--
-- Name: sbi_dist_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE sbi_dist_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sbi_dist_seq OWNER TO postgres;

--
-- Name: sbi_distribution; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE sbi_distribution (
    sbi_dist_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    credit_polling_account character varying(255),
    detailed_head character varying(255),
    group_sub_head character varying(255),
    head_end_code character varying(255),
    head_type character varying(255),
    hoa_description character varying(255),
    major_head character varying(255),
    major_sub_head character varying(255),
    minor_sub_head character varying(255),
    status integer,
    sub_detailed_head character varying(255),
    sub_head character varying(255)
);


ALTER TABLE sbi_distribution OWNER TO postgres;

--
-- Name: second_vehicle_details; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE second_vehicle_details (
    second_vehicle_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    address character varying(255),
    chassis_no character varying(255),
    dob character varying(255),
    engine_no character varying(255),
    iteration integer,
    owner_father_name character varying(255),
    owner_last_name character varying(255),
    owner_name character varying(255),
    paid_tax boolean,
    registration_no character varying(255),
    user_id bigint,
    vehicle_rc_id bigint,
    rejection_history_id bigint,
    version integer DEFAULT 0 NOT NULL,
    is_valid_second_vehicle boolean DEFAULT true
);


ALTER TABLE second_vehicle_details OWNER TO postgres;

--
-- Name: second_vehicle_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE second_vehicle_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE second_vehicle_seq OWNER TO postgres;

--
-- Name: special_no_rtaseq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE special_no_rtaseq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE special_no_rtaseq OWNER TO postgres;

--
-- Name: special_no_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE special_no_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE special_no_seq OWNER TO postgres;

--
-- Name: special_number; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE special_number (
    special_no_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    cost double precision,
    number integer
);


ALTER TABLE special_number OWNER TO postgres;

--
-- Name: special_number_fee; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE special_number_fee
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE special_number_fee OWNER TO postgres;

--
-- Name: special_number_fee_details; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE special_number_fee_details (
    special_number_fee_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    amount double precision,
    bank_name character varying(255),
    dd_number character varying(255),
    pr_number character varying(255),
    vehical_rc_id bigint,
    transaction_id character varying(255)
);


ALTER TABLE special_number_fee_details OWNER TO postgres;

--
-- Name: special_number_rta_office; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE special_number_rta_office (
    special_no_rta_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    number character varying(255),
    special_number character varying(255),
    status integer,
    tr_number character varying(255)
);


ALTER TABLE special_number_rta_office OWNER TO postgres;

--
-- Name: state; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE state (
    state_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    name character varying(255),
    status boolean,
    code character varying(50),
    country_id bigint
);


ALTER TABLE state OWNER TO postgres;

--
-- Name: state_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE state_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE state_id_seq OWNER TO postgres;

--
-- Name: status_reference; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE status_reference (
    status_code character varying(255) NOT NULL,
    created_by character varying(50),
    created_on date,
    modified_by character varying(50),
    modified_on date,
    is_active character varying(255),
    status character varying(255)
);


ALTER TABLE status_reference OWNER TO postgres;

--
-- Name: stoppage_application; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE stoppage_application (
    stoppage_app_id bigint NOT NULL,
    stoppage_no character varying(50),
    inspected_by character varying(50),
    inspected_dt bigint,
    regn_no character varying(50),
    vehicle_rc_id bigint,
    rc_validity bigint,
    fc_validity bigint,
    permit_validity bigint,
    tax_validity bigint,
    tax_amt double precision,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    stoppage_on bigint,
    stoppage_reason character varying(255),
    status integer,
    stoppage_area character varying(255),
    stoppage_addr1 character varying(255),
    stoppage_addr2 character varying(255),
    stoppage_addr3 character varying(255),
    approved character(1),
    approved_by character varying(50),
    approved_on bigint,
    valid_flag character(1),
    assigned_mvi character varying(50),
    mvi_remarks character varying(255),
    office_code character varying(50),
    vehicle_cov character varying(50),
    remarks character varying(255),
    pms_remarks character varying(255),
    tax_exem_months integer,
    city character varying(255),
    district_name character varying(255),
    state_name character varying(255),
    pincode bigint
);


ALTER TABLE stoppage_application OWNER TO postgres;

--
-- Name: stoppage_application_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE stoppage_application_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE stoppage_application_seq OWNER TO postgres;

--
-- Name: stoppage_fee; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE stoppage_fee (
    stoppage_fee_id bigint NOT NULL,
    stoppage_no character varying(50),
    inspected_by character varying(50),
    inspected_dt bigint,
    revocation_dt bigint,
    regn_no character varying(50),
    tax_amt double precision,
    vehicle_rc_id bigint,
    quarter_from_dt bigint,
    quarter_end_dt bigint,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint
);


ALTER TABLE stoppage_fee OWNER TO postgres;

--
-- Name: stoppage_fee_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE stoppage_fee_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE stoppage_fee_seq OWNER TO postgres;

--
-- Name: stoppage_inspection; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE stoppage_inspection (
    stoppage_inspection_id bigint NOT NULL,
    stoppage_no character varying(50),
    inspected_by character varying(50),
    inspected_dt bigint,
    regn_no character varying(50),
    vehicle_rc_id bigint,
    quarter_from_dt bigint,
    quarter_end_dt bigint,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    all_check_posts character varying(255),
    garaged_place_reported boolean,
    is_repairs_condemned boolean,
    is_verified_gvr boolean,
    mechanics_statements character varying(255),
    oil_bunks character varying(255),
    other character varying(255),
    over_all_comments character varying(255),
    owner character varying(255),
    repairs_comments character varying(255),
    repairs_condemned_comments character varying(255),
    shifted_comment character varying(255)
);


ALTER TABLE stoppage_inspection OWNER TO postgres;

--
-- Name: stoppage_inspection_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE stoppage_inspection_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE stoppage_inspection_seq OWNER TO postgres;

--
-- Name: stoppage_revocation; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE stoppage_revocation (
    stoppage_revocation_id bigint NOT NULL,
    stoppage_no character varying(50),
    inspected_by character varying(50),
    inspected_dt bigint,
    revocation_dt bigint,
    regn_no character varying(50),
    vehicle_rc_id bigint,
    approved_by character varying(50),
    approved_on bigint,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint
);


ALTER TABLE stoppage_revocation OWNER TO postgres;

--
-- Name: stoppage_revocation_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE stoppage_revocation_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE stoppage_revocation_seq OWNER TO postgres;

--
-- Name: susp_drivers_license_dtls; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE susp_drivers_license_dtls (
    dl_sequence_id integer NOT NULL,
    licence_holder_id bigint NOT NULL,
    created_by character varying(50),
    created_on date,
    modified_by character varying(50),
    modified_on date,
    application_id character varying(255),
    approved_ao character varying(255),
    approved_mvi character varying(255),
    dl_no character varying(255),
    lic_status character varying(255),
    module_cd character varying(255),
    office_memono character varying(255),
    offmemo_dt date,
    order_dt date,
    order_no character varying(255),
    planned_valid_from date,
    planned_valid_to date,
    punishment_desc character varying(255),
    reference_dt date,
    reference_id character varying(255),
    rev_dt date,
    revokereason character varying(255),
    rta_office_code bigint,
    scissued character varying(255),
    section_rule character varying(255),
    source character varying(255),
    status_code character varying(255),
    status_date date,
    status_remarks character varying(255),
    status_updated_by character varying(255),
    status_updated_on date,
    status_valid_from date,
    status_valid_to date,
    sus_can character varying(255),
    ticket_details character varying(255)
);


ALTER TABLE susp_drivers_license_dtls OWNER TO postgres;

--
-- Name: suspended_rc_no; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE suspended_rc_no (
    suspended_rc_no_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    comment character varying(255),
    end_date bigint,
    is_revoked boolean,
    pr_number character varying(255),
    start_date bigint,
    status integer,
    description character varying(100),
    raised_by character varying(100),
    section_rule character varying(255),
    source character varying(255)
);


ALTER TABLE suspended_rc_no OWNER TO postgres;

--
-- Name: suspended_rc_no_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE suspended_rc_no_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE suspended_rc_no_seq OWNER TO postgres;

--
-- Name: tax_detail; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tax_detail (
    tax_dtl_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    hpa_fee double precision,
    hsrp_fee double precision,
    penalty double precision,
    second_vehicle_tax double precision,
    second_vehicle_tax_amt double precision,
    second_vehicle_tax_on bigint,
    tax_amt double precision NOT NULL,
    total_amt double precision,
    tr_fee double precision,
    tr_service_charge double precision,
    tax_master_id bigint NOT NULL,
    tax_type_id bigint NOT NULL,
    vehicle_rc_id bigint NOT NULL,
    tax_valid_upto bigint,
    cess_fee double precision,
    cess_fee_valid_upto bigint,
    version integer DEFAULT 0 NOT NULL,
    columnregistration_type integer,
    exe_flg character(1),
    pms_remarks character varying(1000),
    releaseorder_fee numeric,
    lttent_tdt bigint,
    lttent_fdt bigint,
    lttenttax_amnt numeric,
    gt_flag character(1),
    demandtax_arrears numeric,
    twotire character(1),
    vcr_no character varying(35),
    demandpenalty_arrears numeric,
    refundable_flg character(1),
    tax_pymntlastdate bigint,
    remarks character varying(1000),
    exmpt_flg character(1),
    pymnt_start_dt bigint,
    refund_done_flg character(1),
    court_flg character varying(1),
    appln_no character varying(19),
    go_no character varying(20),
    transactn_status character varying(1),
    proportionate_amt numeric,
    paymt_dt bigint,
    balance_amt numeric,
    permit_type character varying(5),
    demand_dt bigint,
    class_of_veh character varying(10),
    late_fee_amt numeric,
    regn_no character varying(32),
    permit_no character varying(31),
    compound_fee numeric,
    chalan_no character varying(50),
    office_cd bigint,
    demand_amt numeric,
    green_tax_amt double precision DEFAULT 0,
    green_tax_valid_to bigint,
    offline_payment character varying(50),
    offline_challan_no character varying(50)
);


ALTER TABLE tax_detail OWNER TO postgres;

--
-- Name: tax_detail_history; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tax_detail_history (
    tax_hist_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    appln_no character varying(19),
    balance_amt numeric(19,2),
    cess_fee double precision,
    cess_fee_valid_upto bigint,
    chalan_no character varying(50),
    class_of_veh character varying(10),
    columnregistration_type integer,
    compound_fee numeric(19,2),
    court_flg character varying(1),
    demand_amt numeric(19,2),
    demand_dt bigint,
    demandpenalty_arrears numeric(19,2),
    demandtax_arrears numeric(19,2),
    exe_flg character(1),
    exmpt_flg character(1),
    go_no character varying(20),
    green_tax_amt double precision DEFAULT 0,
    green_tax_valid_to bigint,
    gt_flag character(1),
    hpa_fee double precision,
    hsrp_fee double precision,
    late_fee_amt numeric(19,2),
    lttent_fdt bigint,
    lttent_tdt bigint,
    lttenttax_amnt numeric(19,2),
    office_cd bigint,
    paymt_dt bigint,
    penalty double precision,
    permit_no character varying(31),
    permit_type character varying(5),
    pms_remarks character varying(1000),
    proportionate_amt numeric(19,2),
    pymnt_start_dt bigint,
    refund_done_flg character(1),
    refundable_flg character(1),
    regn_no character varying(32),
    releaseorder_fee numeric(19,2),
    remarks character varying(1000),
    second_vehicle_tax double precision,
    second_vehicle_tax_amt double precision,
    second_vehicle_tax_on bigint,
    tax_amt double precision,
    tax_master_id bigint NOT NULL,
    tax_pymntlastdate bigint,
    tax_type_id bigint NOT NULL,
    tax_valid_upto bigint,
    total_amt double precision,
    tr_fee double precision,
    tr_service_charge double precision,
    transactn_status character varying(1),
    twotire character(1),
    vcr_no character varying(35),
    vehicle_rc_id bigint NOT NULL
);


ALTER TABLE tax_detail_history OWNER TO postgres;

--
-- Name: tax_dtl_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tax_dtl_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tax_dtl_seq OWNER TO postgres;

--
-- Name: tax_hist_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tax_hist_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tax_hist_seq OWNER TO postgres;

--
-- Name: tax_master; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tax_master (
    tax_master_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    price_from double precision,
    price_to double precision,
    tax_percent double precision,
    vehicle_no integer,
    vehicle_type character varying(255)
);


ALTER TABLE tax_master OWNER TO postgres;

--
-- Name: tax_master_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tax_master_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tax_master_seq OWNER TO postgres;

--
-- Name: tax_type; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tax_type (
    tax_type_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    name character varying(255),
    status boolean,
    code character varying(50),
    percentage double precision
);


ALTER TABLE tax_type OWNER TO postgres;

--
-- Name: tax_type_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tax_type_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tax_type_seq OWNER TO postgres;

--
-- Name: temp_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE temp_seq
    START WITH 32437225
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE temp_seq OWNER TO postgres;

--
-- Name: tmp_new_vrc_mig; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tmp_new_vrc_mig (
    vehicle_rc_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    ao_action_status integer,
    ao_user_id bigint,
    cco_action_status integer,
    cco_user_id bigint,
    dealer_action_status integer,
    dealer_id bigint,
    mvi_action_status integer,
    mvi_user_id bigint,
    rto_action_status integer,
    rto_user_id bigint,
    iteration integer
);


ALTER TABLE tmp_new_vrc_mig OWNER TO postgres;

--
-- Name: tmp_new_vrc_mig_trpr; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tmp_new_vrc_mig_trpr (
    vehicle_rc_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    ao_action_status integer,
    ao_user_id bigint,
    cco_action_status integer,
    cco_user_id bigint,
    dealer_action_status integer,
    dealer_id bigint,
    mvi_action_status integer,
    mvi_user_id bigint,
    rto_action_status integer,
    rto_user_id bigint,
    iteration integer
);


ALTER TABLE tmp_new_vrc_mig_trpr OWNER TO postgres;

--
-- Name: tr_series_master; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tr_series_master (
    tr_series_master_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    end_number integer,
    series character varying(255),
    start_number integer,
    status integer,
    use_number integer,
    district_id bigint
);


ALTER TABLE tr_series_master OWNER TO postgres;

--
-- Name: tr_series_master_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tr_series_master_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tr_series_master_seq OWNER TO postgres;

--
-- Name: tran_dtl_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tran_dtl_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tran_dtl_seq OWNER TO postgres;

--
-- Name: trans_hist_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE trans_hist_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE trans_hist_seq OWNER TO postgres;

--
-- Name: transaction_detail; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE transaction_detail (
    transaction_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    account_type character varying(255),
    amount double precision,
    payment_time bigint,
    payment_type integer,
    sbi_ref_no character varying(255),
    status integer NOT NULL,
    status_message character varying(255),
    transaction_no character varying(255) NOT NULL,
    dealer_invc_id bigint,
    vehicle_rc_id bigint NOT NULL,
    fee_amt double precision,
    hsrp_amt double precision,
    pay_amount double precision,
    postal_charge double precision,
    service_charge double precision,
    tax_amt double precision,
    permit_amt double precision,
    version integer DEFAULT 0 NOT NULL,
    cess_fee double precision,
    pg_type character varying(50)
);


ALTER TABLE transaction_detail OWNER TO postgres;

--
-- Name: transaction_history; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE transaction_history (
    trans_hist_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    payment_type integer,
    request_parameter text,
    response_parameter text,
    status integer NOT NULL,
    transaction_no character varying(255),
    transaction_detail_id bigint,
    vehicle_rc_id bigint,
    pg_type character varying(50)
);


ALTER TABLE transaction_history OWNER TO postgres;

--
-- Name: user_attachment_details; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE user_attachment_details (
    attachment_detail_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    attachment_from integer NOT NULL,
    attachment_title character varying(200) NOT NULL,
    file_name character varying(200) NOT NULL,
    source character varying(255) NOT NULL,
    status integer NOT NULL,
    user_role integer,
    doc_master_id integer NOT NULL,
    user_id bigint NOT NULL
);


ALTER TABLE user_attachment_details OWNER TO postgres;

--
-- Name: user_attachment_details_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE user_attachment_details_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE user_attachment_details_sequence OWNER TO postgres;

--
-- Name: user_history_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE user_history_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE user_history_seq OWNER TO postgres;

--
-- Name: user_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE user_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE user_seq OWNER TO postgres;

--
-- Name: user_transfer_history; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE user_transfer_history (
    user_transfer_id bigint NOT NULL,
    transfer_by character varying(255),
    transfer_date bigint,
    transfer_from character varying(255),
    transfer_to character varying(255),
    user_name character varying(255),
    user_role character varying(255)
);


ALTER TABLE user_transfer_history OWNER TO postgres;

--
-- Name: user_transfer_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE user_transfer_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE user_transfer_sequence OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE users (
    user_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    email character varying(255),
    first_name character varying(255),
    last_login bigint,
    last_name character varying(255),
    middle_name character varying(255),
    mobile character varying(20),
    password character varying(255),
    phone character varying(20),
    status boolean,
    user_name character varying(255),
    user_type character varying(50),
    aadhar_number character varying(255),
    business_desc character varying(255),
    company_type character varying(255),
    consent boolean,
    firm_reg_date bigint,
    institution_name character varying(255),
    pan_number character varying(255),
    roc_number character varying(255)
);


ALTER TABLE users OWNER TO postgres;

--
-- Name: vahan_master; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vahan_master (
    id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    body_type_desc character varying(255),
    chassis_no character varying(255),
    color character varying(255),
    cubic_capacity double precision,
    engine_no character varying(255),
    engine_power double precision,
    front_axle_desc character varying(255),
    front_axle_weight double precision,
    fuel_used character varying(255),
    height bigint,
    length bigint,
    makers_class character varying(255),
    makers_desc character varying(255),
    mfg_date character varying(255),
    no_cyl integer,
    o1_axle_desc character varying(255),
    o1_axle_weight integer,
    o2_axle_desc character varying(255),
    o2_axle_weight integer,
    o3_axle_desc character varying(255),
    o3_axle_weight integer,
    o4_axle_desc character varying(255),
    o4_axle_weight integer,
    o5_axle_desc character varying(255),
    o5_axle_weight integer,
    pollution_norms_desc character varying(255),
    rear_axle_desc character varying(255),
    rear_axle_weight integer,
    laden_weight bigint,
    seat_capacity integer,
    stand_capacity integer,
    tandem_axel_desc character varying(255),
    tandem_axel_weight integer,
    unladen_weight bigint,
    vehicle_class character varying(255),
    wheelbase integer,
    width bigint,
    source character varying(80)
);


ALTER TABLE vahan_master OWNER TO postgres;

--
-- Name: vcr_details; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vcr_details (
    vcr_id bigint NOT NULL,
    bank_payment_status integer,
    booked_by_mvi character varying(255),
    booked_date character varying(255),
    booked_time character varying(255),
    dl_number character varying(255),
    exemption_flag boolean,
    id_type character varying(255),
    booked_place character varying(255),
    pliedas character varying(255),
    reg_number character varying(255),
    transaction_id bigint,
    vcr_fine_amount double precision,
    vcr_number character varying(255),
    vcr_status character varying(255),
    vcr_tax_amount double precision,
    vehicle_rc_id character varying(255),
    sezd_flag character varying(255),
    version integer DEFAULT 0 NOT NULL
);


ALTER TABLE vcr_details OWNER TO postgres;

--
-- Name: vcr_details_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE vcr_details_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vcr_details_seq OWNER TO postgres;

--
-- Name: vcr_offense_logs; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vcr_offense_logs (
    id bigint NOT NULL,
    fine_amount character varying(255),
    offense character varying(255),
    vcr_numvber character varying(255),
    vcr_offense_id bigint
);


ALTER TABLE vcr_offense_logs OWNER TO postgres;

--
-- Name: vcr_offense_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE vcr_offense_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vcr_offense_seq OWNER TO postgres;

--
-- Name: vcr_payment_logs; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vcr_payment_logs (
    id bigint NOT NULL,
    challan_no character varying(255),
    payment_dt character varying(255),
    regn_no character varying(255),
    transaction_amount character varying(255),
    transaction_dt character varying(255),
    transaction_no character varying(255),
    vcr_api_status_flag character varying(255),
    vcr_number character varying(255)
);


ALTER TABLE vcr_payment_logs OWNER TO postgres;

--
-- Name: vcr_tran_dtl_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE vcr_tran_dtl_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vcr_tran_dtl_seq OWNER TO postgres;

--
-- Name: vcr_transaction_details; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vcr_transaction_details (
    vcr_transaction_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    fee_amt double precision,
    account_type character varying(255),
    compound_fee integer,
    hsrp_amt double precision,
    pay_amount double precision,
    payment_time bigint,
    payment_type integer,
    postal_charge double precision,
    pr_number character varying(255) NOT NULL,
    sbi_ref_no character varying(255),
    service_charge double precision,
    status integer NOT NULL,
    status_message character varying(255),
    tax_amt double precision,
    vcr_challan_no character varying(255) NOT NULL,
    vcr_transaction_no character varying(255) NOT NULL,
    version integer DEFAULT 0 NOT NULL,
    vehicle_rc_id bigint NOT NULL
);


ALTER TABLE vcr_transaction_details OWNER TO postgres;

--
-- Name: vcrpayment_log_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE vcrpayment_log_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vcrpayment_log_seq OWNER TO postgres;

--
-- Name: vehicle_alter_attachment_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE vehicle_alter_attachment_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vehicle_alter_attachment_sequence OWNER TO postgres;

--
-- Name: vehicle_alteration_attachment; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vehicle_alteration_attachment (
    vehicle_alter_attachment_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    attachment_from integer NOT NULL,
    attachment_title character varying(200) NOT NULL,
    file_name character varying(200) NOT NULL,
    source character varying(255) NOT NULL,
    status integer NOT NULL,
    user_role integer,
    doc_master_id integer NOT NULL,
    vehicle_rc_id bigint NOT NULL
);


ALTER TABLE vehicle_alteration_attachment OWNER TO postgres;

--
-- Name: vehicle_alteration_details; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vehicle_alteration_details (
    alteration_details_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    alteration_category integer,
    body_type character varying(255),
    completion_date bigint,
    fuel_used character varying(255),
    height bigint,
    length bigint,
    seating_capacity integer,
    status integer DEFAULT 1,
    vehicle_sub_class character varying(255),
    width bigint,
    vehicle_rc_id bigint NOT NULL,
    reg_no_type integer,
    color character varying(255),
    ulw bigint,
    rlw bigint,
    app_no character varying(100),
    request_id integer DEFAULT 0
);


ALTER TABLE vehicle_alteration_details OWNER TO postgres;

--
-- Name: vehicle_alteration_details_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE vehicle_alteration_details_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vehicle_alteration_details_seq OWNER TO postgres;

--
-- Name: vehicle_bharat_stage; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vehicle_bharat_stage (
    id bigint NOT NULL,
    chassis_number character varying(255) NOT NULL,
    engine_number character varying(99) NOT NULL,
    is_migrated boolean NOT NULL,
    policy_start_date character varying(255) NOT NULL
);


ALTER TABLE vehicle_bharat_stage OWNER TO postgres;

--
-- Name: vehicle_bharat_stage_backup; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vehicle_bharat_stage_backup (
    id bigint NOT NULL,
    chassis_number character varying(255) NOT NULL,
    engine_number character varying(99) NOT NULL,
    is_migrated boolean NOT NULL,
    policy_start_date character varying(255) NOT NULL
);


ALTER TABLE vehicle_bharat_stage_backup OWNER TO postgres;

--
-- Name: vehicle_bharat_stage_duplicates; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vehicle_bharat_stage_duplicates (
    id bigint NOT NULL,
    chassis_number character varying(255) NOT NULL,
    engine_number character varying(99) NOT NULL,
    is_migrated boolean NOT NULL,
    policy_start_date character varying(255) NOT NULL
);


ALTER TABLE vehicle_bharat_stage_duplicates OWNER TO postgres;

--
-- Name: vehicle_billing_details; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vehicle_billing_details (
    vehicle_billing_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    helmet_invoice_amount double precision,
    invoice_date bigint NOT NULL,
    invoice_no character varying(255) NOT NULL,
    invoice_value double precision NOT NULL,
    vehicle_dtl_id bigint NOT NULL,
    version integer DEFAULT 0 NOT NULL
);


ALTER TABLE vehicle_billing_details OWNER TO postgres;

--
-- Name: vehicle_billing_details_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE vehicle_billing_details_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vehicle_billing_details_seq OWNER TO postgres;

--
-- Name: vehicle_change_history_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE vehicle_change_history_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vehicle_change_history_seq OWNER TO postgres;

--
-- Name: vehicle_class; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vehicle_class (
    vehicle_class_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    vehicle_category character varying(255),
    vehicle_class_code character varying(255)
);


ALTER TABLE vehicle_class OWNER TO postgres;

--
-- Name: vehicle_class_bck; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vehicle_class_bck (
    vehicle_class_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    vehicle_category character varying(255),
    vehicle_class_code character varying(255)
);


ALTER TABLE vehicle_class_bck OWNER TO postgres;

--
-- Name: vehicle_class_desc; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vehicle_class_desc (
    vehicle_class_desc_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    alteration_category integer,
    code character varying(255),
    description character varying(255),
    vehicle_class_id bigint
);


ALTER TABLE vehicle_class_desc OWNER TO postgres;

--
-- Name: vehicle_class_desc_bck; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vehicle_class_desc_bck (
    vehicle_class_desc_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    code character varying(255),
    description character varying(255),
    vehicle_class_id bigint,
    alteration_category integer
);


ALTER TABLE vehicle_class_desc_bck OWNER TO postgres;

--
-- Name: vehicle_class_desc_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE vehicle_class_desc_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vehicle_class_desc_id_seq OWNER TO postgres;

--
-- Name: vehicle_class_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE vehicle_class_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vehicle_class_id_seq OWNER TO postgres;

--
-- Name: vehicle_current_tax_details; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vehicle_current_tax_details (
    vehicle_tax_dtl_id bigint NOT NULL,
    tax_type_id bigint,
    tax_valid_upto bigint,
    tax_valid_from bigint,
    vehicle_rc_id bigint,
    vehicle_cov character varying(50),
    status integer,
    stoppage_flag boolean,
    exemption_flag boolean,
    remarks character varying(255),
    vehicle_reg_no character varying(50),
    version integer,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    tax_amt double precision
);


ALTER TABLE vehicle_current_tax_details OWNER TO postgres;

--
-- Name: vehicle_current_tax_details_dup; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vehicle_current_tax_details_dup (
    vehicle_tax_dtl_id bigint NOT NULL,
    tax_type_id bigint NOT NULL,
    tax_valid_upto bigint,
    tax_valid_from bigint,
    vehicle_rc_id bigint NOT NULL,
    vehicle_cov character varying(50),
    status integer,
    stoppage_flag boolean,
    exemption_flag boolean,
    remarks character varying(255),
    vehicle_reg_no character varying(50),
    version integer DEFAULT 0 NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    tax_amt double precision NOT NULL
);


ALTER TABLE vehicle_current_tax_details_dup OWNER TO postgres;

--
-- Name: vehicle_current_tax_details_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE vehicle_current_tax_details_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vehicle_current_tax_details_seq OWNER TO postgres;

--
-- Name: vehicle_details; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vehicle_details (
    vehicle_dtl_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    chassis_no character varying(255) NOT NULL,
    color character varying(255) NOT NULL,
    engine_no character varying(255) NOT NULL,
    fuel_used character varying(255) NOT NULL,
    maker_class character varying(255),
    maker_name character varying(255) NOT NULL,
    mfg_date character varying(255) NOT NULL,
    rlw bigint NOT NULL,
    seating_capacity integer,
    ulw bigint NOT NULL,
    vehicle_class character varying(255) NOT NULL,
    vehicle_number integer,
    vehicle_sub_class character varying(255) NOT NULL,
    tax_type_id bigint NOT NULL,
    vehicle_rc_id bigint NOT NULL,
    alteration_category integer,
    body_type_update character varying(255),
    completion_date bigint,
    gas_kit_number character varying(255),
    height_update bigint,
    length_update bigint,
    vehcle_category integer DEFAULT 0,
    version integer DEFAULT 0 NOT NULL,
    wheelbase integer,
    width_update bigint,
    old_veh_id bigint,
    multiaxle character varying(1),
    old_pkey character varying(255)
);


ALTER TABLE vehicle_details OWNER TO postgres;

--
-- Name: vehicle_details_dup; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vehicle_details_dup (
    vehicle_dtl_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    chassis_no character varying(255) NOT NULL,
    color character varying(255) NOT NULL,
    engine_no character varying(255) NOT NULL,
    fuel_used character varying(255) NOT NULL,
    maker_class character varying(255),
    maker_name character varying(255) NOT NULL,
    mfg_date character varying(255) NOT NULL,
    rlw bigint NOT NULL,
    seating_capacity integer,
    ulw bigint NOT NULL,
    vehicle_class character varying(255) NOT NULL,
    vehicle_number integer,
    vehicle_sub_class character varying(255) NOT NULL,
    tax_type_id bigint NOT NULL,
    vehicle_rc_id bigint NOT NULL,
    alteration_category integer,
    body_type_update character varying(255),
    completion_date bigint,
    gas_kit_number character varying(255),
    height_update bigint,
    length_update bigint,
    vehcle_category integer DEFAULT 0,
    version integer DEFAULT 0 NOT NULL,
    wheelbase integer,
    width_update bigint,
    old_veh_id bigint,
    multiaxle character varying(1),
    old_pkey character varying(255)
);


ALTER TABLE vehicle_details_dup OWNER TO postgres;

--
-- Name: vehicle_details_history; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vehicle_details_history (
    vehicle_dtl_history_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    alteration_category integer,
    body_type_update character varying(255),
    chassis_no character varying(255) NOT NULL,
    color character varying(255) NOT NULL,
    completion_date bigint,
    engine_no character varying(255) NOT NULL,
    fuel_used character varying(255) NOT NULL,
    gas_kit_number character varying(255),
    height_update bigint,
    length_update bigint,
    maker_class character varying(255),
    maker_name character varying(255) NOT NULL,
    mfg_date character varying(255) NOT NULL,
    rlw bigint NOT NULL,
    seating_capacity integer,
    service_code character varying(255),
    ulw bigint NOT NULL,
    vehcle_category integer DEFAULT 0,
    vehicle_class character varying(255) NOT NULL,
    vehicle_dtl_id bigint,
    vehicle_number integer,
    vehicle_sub_class character varying(255) NOT NULL,
    wheelbase integer,
    width_update bigint,
    tax_type_id bigint NOT NULL,
    vehicle_rc_id bigint NOT NULL,
    multiaxle character varying(1),
    old_veh_id bigint
);


ALTER TABLE vehicle_details_history OWNER TO postgres;

--
-- Name: vehicle_dtl_history_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE vehicle_dtl_history_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vehicle_dtl_history_seq OWNER TO postgres;

--
-- Name: vehicle_dtl_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE vehicle_dtl_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vehicle_dtl_seq OWNER TO postgres;

--
-- Name: vehicle_duplicate_registration; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vehicle_duplicate_registration (
    vehicle_dup_reg_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    comments character varying(255),
    duplicate_reason character varying(255),
    vehicle_rc_id bigint NOT NULL
);


ALTER TABLE vehicle_duplicate_registration OWNER TO postgres;

--
-- Name: vehicle_duplicate_registration_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE vehicle_duplicate_registration_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vehicle_duplicate_registration_seq OWNER TO postgres;

--
-- Name: vehicle_finance_details; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vehicle_finance_details (
    id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    cheque_no bigint,
    finance_date_of_agreement bigint,
    end_dt bigint,
    finance_amount double precision,
    finance_emi real,
    finance_terminated boolean,
    financer_b_id bigint,
    financer_id bigint,
    financer_mode integer NOT NULL,
    intrest_rate real,
    payment_mode integer,
    rto_approved integer,
    finance_tenure integer,
    vehicle_rc_id bigint NOT NULL
);


ALTER TABLE vehicle_finance_details OWNER TO postgres;

--
-- Name: vehicle_noc; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vehicle_noc (
    noc_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    noc_application_date bigint,
    noc_cancellation_date bigint,
    noc_issue_date bigint,
    status boolean,
    noc_address_id bigint NOT NULL,
    vehicle_rc_id bigint NOT NULL,
    old_pkey character varying(255)
);


ALTER TABLE vehicle_noc OWNER TO postgres;

--
-- Name: vehicle_noc_address; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vehicle_noc_address (
    nco_address_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    noc_address character varying(255) NOT NULL,
    noc_address_code character varying(255) NOT NULL,
    status boolean,
    district_id bigint NOT NULL,
    rta_office_id bigint NOT NULL,
    old_pkey character varying(255)
);


ALTER TABLE vehicle_noc_address OWNER TO postgres;

--
-- Name: vehicle_noc_history; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vehicle_noc_history (
    noc_history_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    noc_application_date bigint,
    noc_cancellation_date bigint,
    noc_issue_date bigint,
    noc_id bigint,
    service_code character varying(255),
    status boolean,
    noc_address_id bigint NOT NULL,
    vehicle_rc_id bigint NOT NULL
);


ALTER TABLE vehicle_noc_history OWNER TO postgres;

--
-- Name: vehicle_pr_release; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vehicle_pr_release (
    vehicle_pr_release_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    pr_number character varying(255) NOT NULL,
    pr_release_status boolean,
    vehicle_rc_id bigint NOT NULL,
    app_no character varying,
    service_type character varying
);


ALTER TABLE vehicle_pr_release OWNER TO postgres;

--
-- Name: vehicle_pr_release_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE vehicle_pr_release_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vehicle_pr_release_seq OWNER TO postgres;

--
-- Name: vehicle_puc; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vehicle_puc (
    puc_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    puc_co real,
    puc_expiry_date bigint,
    puc_hc real,
    puc_hsu real,
    puc_issue_date bigint,
    puc_k_avg real,
    status boolean,
    vehicle_rc_id bigint NOT NULL
);


ALTER TABLE vehicle_puc OWNER TO postgres;

--
-- Name: vehicle_puc_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE vehicle_puc_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vehicle_puc_seq OWNER TO postgres;

--
-- Name: vehicle_quarterly; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vehicle_quarterly (
    vehicle_quarterly_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    status bigint,
    vehicle_code character varying(255),
    vehicle_weight character varying(255)
);


ALTER TABLE vehicle_quarterly OWNER TO postgres;

--
-- Name: vehicle_quarterly_bck; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vehicle_quarterly_bck (
    vehicle_quarterly_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    status bigint,
    vehicle_code character varying(255),
    vehicle_weight character varying(255)
);


ALTER TABLE vehicle_quarterly_bck OWNER TO postgres;

--
-- Name: vehicle_quarterly_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE vehicle_quarterly_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vehicle_quarterly_seq OWNER TO postgres;

--
-- Name: vehicle_rc; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vehicle_rc (
    vehicle_rc_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    aadhar_no character varying(30),
    chassis_no character varying(255),
    current_step integer NOT NULL,
    is_migrated boolean,
    iteration integer,
    ownership_type integer NOT NULL,
    pr_issue_time bigint,
    pr_number character varying(255),
    pr_status integer,
    process_status integer,
    tr_issue_time bigint,
    tr_number character varying(255),
    tr_status integer NOT NULL,
    reg_category integer NOT NULL,
    rta_office_id bigint NOT NULL,
    user_id bigint NOT NULL,
    is_printed boolean,
    rc_print_date bigint,
    doc_upload_consent boolean,
    is_aadhar_verified boolean,
    pr_type integer,
    ao_action_status integer,
    application_number character varying(255),
    cco_action_status integer,
    migration_source character varying(255),
    mvi_action_status integer,
    pr_expire_date bigint,
    rto_action_status integer,
    service_code character varying(255),
    ao_user_id bigint,
    cco_user_id bigint,
    mvi_user_id bigint,
    rto_user_id bigint,
    version integer DEFAULT 0 NOT NULL,
    cust_dtl_id bigint,
    old_applicant_id character varying(80),
    vehicle_dtl_id bigint,
    old_pkey character varying(255),
    is_incomplete_data boolean DEFAULT false NOT NULL,
    incomplete_reason character varying(255)
);


ALTER TABLE vehicle_rc OWNER TO postgres;

--
-- Name: vehicle_rc_change_history; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vehicle_rc_change_history (
    vehicle_rc_change_history_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    aadhar_no character varying(255),
    ao_action_status integer,
    application_number character varying(255),
    cco_action_status integer,
    chassis_no character varying(255),
    current_step integer NOT NULL,
    cust_dtl_id bigint,
    doc_upload_consent boolean DEFAULT false,
    is_aadhar_verified boolean DEFAULT false,
    is_migrated boolean,
    is_printed boolean DEFAULT false,
    iteration integer,
    migration_source character varying(255),
    mvi_action_status integer,
    old_applicant_id character varying(80),
    ownership_type integer NOT NULL,
    pr_expire_date bigint,
    pr_issue_time bigint,
    pr_number character varying(255),
    pr_status integer,
    pr_type integer,
    process_status integer,
    rc_print_date bigint,
    rto_action_status integer,
    service_code character varying(255),
    tr_issue_time bigint,
    tr_number character varying(255),
    tr_status integer NOT NULL,
    vehicle_dtl_id bigint,
    vehicle_rc_id bigint,
    version integer DEFAULT 0 NOT NULL,
    ao_user_id bigint,
    cco_user_id bigint,
    mvi_user_id bigint,
    reg_category integer NOT NULL,
    rta_office_id bigint NOT NULL,
    rto_user_id bigint,
    user_id bigint NOT NULL
);


ALTER TABLE vehicle_rc_change_history OWNER TO postgres;

--
-- Name: vehicle_rc_dup; Type: TABLE; Schema: public; Owner: rtamigprod; Tablespace: 
--

CREATE TABLE vehicle_rc_dup (
    vehicle_rc_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    aadhar_no character varying(30),
    chassis_no character varying(255),
    current_step integer NOT NULL,
    is_migrated boolean,
    iteration integer,
    ownership_type integer NOT NULL,
    pr_issue_time bigint,
    pr_number character varying(255),
    pr_status integer,
    process_status integer,
    tr_issue_time bigint,
    tr_number character varying(255),
    tr_status integer NOT NULL,
    reg_category integer NOT NULL,
    rta_office_id bigint NOT NULL,
    user_id bigint NOT NULL,
    is_printed boolean,
    rc_print_date bigint,
    doc_upload_consent boolean DEFAULT false,
    is_aadhar_verified boolean DEFAULT false,
    pr_type integer,
    ao_action_status integer,
    application_number character varying(255),
    cco_action_status integer,
    migration_source character varying(255),
    mvi_action_status integer,
    pr_expire_date bigint,
    rto_action_status integer,
    service_code character varying(255),
    ao_user_id bigint,
    cco_user_id bigint,
    mvi_user_id bigint,
    rto_user_id bigint,
    version integer DEFAULT 0 NOT NULL,
    cust_dtl_id bigint,
    old_applicant_id character varying(80),
    vehicle_dtl_id bigint,
    old_pkey character varying(255)
);


ALTER TABLE vehicle_rc_dup OWNER TO rtamigprod;

--
-- Name: vehicle_rc_history; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vehicle_rc_history (
    vehicle_rc_history_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    comment character varying(255),
    ip_address character varying(255),
    iteration integer,
    rta_employee_type integer,
    status integer,
    user_id bigint NOT NULL,
    vehicle_rc_id bigint NOT NULL
);


ALTER TABLE vehicle_rc_history OWNER TO postgres;

--
-- Name: vehicle_rc_history_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE vehicle_rc_history_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vehicle_rc_history_seq OWNER TO postgres;

--
-- Name: vehicle_reg_fees_master; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vehicle_reg_fees_master (
    vehicle_reg_fees_master_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    reg_fees_active character varying(255),
    reg_fees_amount double precision,
    reg_fees_type character varying(255),
    veh_reg_category character varying(255),
    veh_reg_class_code character varying(255),
    status integer
);


ALTER TABLE vehicle_reg_fees_master OWNER TO postgres;

--
-- Name: vehicle_reg_fees_master_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE vehicle_reg_fees_master_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vehicle_reg_fees_master_id_seq OWNER TO postgres;

--
-- Name: vehicle_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE vehicle_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vehicle_seq OWNER TO postgres;

--
-- Name: vehicle_theft; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vehicle_theft (
    vehicle_theft_id integer,
    application_no character varying(50) DEFAULT NULL::character varying,
    regn_no character varying(50) DEFAULT NULL::character varying,
    office_cd character varying(50) DEFAULT NULL::character varying,
    vehicle_rc_id bigint,
    applicant_id character varying(80) DEFAULT NULL::character varying,
    crtdusr character varying(50) DEFAULT NULL::character varying,
    crtddt bigint,
    lstupdusr character varying(50) DEFAULT NULL::character varying,
    lstupddt bigint,
    police_station character varying(50) DEFAULT NULL::character varying,
    fir_no character varying(50) DEFAULT NULL::character varying,
    theft_dt bigint,
    objection_rsn character varying(2000) DEFAULT NULL::character varying,
    remarks character varying(2000) DEFAULT NULL::character varying,
    theft_slno numeric NOT NULL,
    twotire character varying(1) DEFAULT NULL::character varying,
    status character varying(10) DEFAULT NULL::character varying,
    revoc_reason character varying(1000) DEFAULT NULL::character varying,
    revocation_date bigint
);


ALTER TABLE vehicle_theft OWNER TO postgres;

--
-- Name: vehicle_theft_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE vehicle_theft_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vehicle_theft_sequence OWNER TO postgres;

--
-- Name: vehicle_training_period; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vehicle_training_period (
    vehicle_class_code character varying(255) NOT NULL,
    created_by character varying(50),
    created_on date,
    modified_by character varying(50),
    modified_on date,
    first_training_period integer,
    renewal_training_period integer
);


ALTER TABLE vehicle_training_period OWNER TO postgres;

--
-- Name: vehicle_weight_master; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vehicle_weight_master (
    vehicle_weight_master_id bigint NOT NULL,
    created_by character varying(50),
    created_on bigint,
    modified_by character varying(50),
    modified_on bigint,
    frm_weight bigint,
    status integer,
    to_weight bigint,
    veh_wgt_catg_code character varying(255),
    veh_wgt_catg_desc character varying(255)
);


ALTER TABLE vehicle_weight_master OWNER TO postgres;

--
-- Name: vehicle_weight_master_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE vehicle_weight_master_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vehicle_weight_master_id_seq OWNER TO postgres;

--
-- Name: aadhaar_logs_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY aadhaar_logs
    ADD CONSTRAINT aadhaar_logs_pkey PRIMARY KEY (id);


--
-- Name: aadhaar_master_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY aadhaar_master
    ADD CONSTRAINT aadhaar_master_pkey PRIMARY KEY (id);


--
-- Name: address_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY address_history
    ADD CONSTRAINT address_history_pkey PRIMARY KEY (address_history_id);


--
-- Name: address_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY address
    ADD CONSTRAINT address_pkey PRIMARY KEY (address_id);


--
-- Name: address_proof_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY address_proof_type
    ADD CONSTRAINT address_proof_type_pkey PRIMARY KEY (address_proof_id);


--
-- Name: affixation_center_master_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY affixation_center_master
    ADD CONSTRAINT affixation_center_master_pkey PRIMARY KEY (affixation_center_id);


--
-- Name: age_group_ref_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY age_group_ref
    ADD CONSTRAINT age_group_ref_pkey PRIMARY KEY (age_group_cd);


--
-- Name: alteration_agency_users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY alteration_agency_users
    ADD CONSTRAINT alteration_agency_users_pkey PRIMARY KEY (alteration_agency_id);


--
-- Name: alteration_cov_mapping_pkey; Type: CONSTRAINT; Schema: public; Owner: rtamigprod; Tablespace: 
--

ALTER TABLE ONLY alteration_cov_mapping
    ADD CONSTRAINT alteration_cov_mapping_pkey PRIMARY KEY (alteration_cov_mapping_id);


--
-- Name: app_version_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY app_version
    ADD CONSTRAINT app_version_pkey PRIMARY KEY (version_id);


--
-- Name: application_slots_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY application_slots
    ADD CONSTRAINT application_slots_pkey PRIMARY KEY (application_slot_id);


--
-- Name: attachment_details_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY attachment_details_history
    ADD CONSTRAINT attachment_details_history_pkey PRIMARY KEY (attachment_detail_history_id);


--
-- Name: attachment_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY attachment_details
    ADD CONSTRAINT attachment_details_pkey PRIMARY KEY (attachment_detail_id);


--
-- Name: body_builder_users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY body_builder_users
    ADD CONSTRAINT body_builder_users_pkey PRIMARY KEY (body_builder_user_id);


--
-- Name: body_type_master_pkey; Type: CONSTRAINT; Schema: public; Owner: rtamigprod; Tablespace: 
--

ALTER TABLE ONLY body_type_master
    ADD CONSTRAINT body_type_master_pkey PRIMARY KEY (body_type_master_id);


--
-- Name: cess_fee_details_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cess_fee_details_history
    ADD CONSTRAINT cess_fee_details_history_pkey PRIMARY KEY (cess_dtl_hist_id);


--
-- Name: cess_fee_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cess_fee_details
    ADD CONSTRAINT cess_fee_details_pkey PRIMARY KEY (cess_fee_dtl_id);


--
-- Name: cfx_notes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cfx_notes
    ADD CONSTRAINT cfx_notes_pkey PRIMARY KEY (cfx_note_id);


--
-- Name: cfx_txn_dtl_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cfx_txn_dtl
    ADD CONSTRAINT cfx_txn_dtl_pkey PRIMARY KEY (cfx_txn_dtl_id);


--
-- Name: challan_number_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY challan_number
    ADD CONSTRAINT challan_number_pkey PRIMARY KEY (challan_number_id);


--
-- Name: chassis_number_unique; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_bharat_stage
    ADD CONSTRAINT chassis_number_unique UNIQUE (chassis_number);


--
-- Name: cms_sync_vehicle_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cms_sync_vehicle
    ADD CONSTRAINT cms_sync_vehicle_pkey PRIMARY KEY (sync_id);


--
-- Name: country_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY country
    ADD CONSTRAINT country_pkey PRIMARY KEY (country_id);


--
-- Name: cust_corporate_details_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cust_corporate_details_history
    ADD CONSTRAINT cust_corporate_details_history_pkey PRIMARY KEY (cust_corp_dtls_history_id);


--
-- Name: cust_corporate_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cust_corporate_details
    ADD CONSTRAINT cust_corporate_details_pkey PRIMARY KEY (cust_corp_dtls_id);


--
-- Name: cust_individual_details_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cust_individual_details_history
    ADD CONSTRAINT cust_individual_details_history_pkey PRIMARY KEY (cust_ind_dtl_history_id);


--
-- Name: cust_individual_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cust_individual_details
    ADD CONSTRAINT cust_individual_details_pkey PRIMARY KEY (cust_ind_dtl_id);


--
-- Name: dealer_invoice_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY dealer_invoice
    ADD CONSTRAINT dealer_invoice_pkey PRIMARY KEY (dealer_invc_id);


--
-- Name: dealer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY dealer
    ADD CONSTRAINT dealer_pkey PRIMARY KEY (dealer_id);


--
-- Name: designation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY designation
    ADD CONSTRAINT designation_pkey PRIMARY KEY (designation_id);


--
-- Name: district_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY district
    ADD CONSTRAINT district_pkey PRIMARY KEY (district_id);


--
-- Name: doc_permission_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY doc_permission
    ADD CONSTRAINT doc_permission_pkey PRIMARY KEY (doc_permission_id);


--
-- Name: doc_types_master_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY doc_types_master
    ADD CONSTRAINT doc_types_master_pkey PRIMARY KEY (doc_type_id);


--
-- Name: drivers_license_dtls_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY drivers_license_dtls_history
    ADD CONSTRAINT drivers_license_dtls_history_pkey PRIMARY KEY (drivers_license_hist_id);


--
-- Name: drivers_license_dtls_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY drivers_license_dtls
    ADD CONSTRAINT drivers_license_dtls_pkey PRIMARY KEY (dl_sequence_id, licence_holder_id);


--
-- Name: driving_institute_users_info_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY driving_institute_users_info
    ADD CONSTRAINT driving_institute_users_info_pkey PRIMARY KEY (driving_institute_users_info_id);


--
-- Name: driving_institute_users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY driving_institute_users
    ADD CONSTRAINT driving_institute_users_pkey PRIMARY KEY (driving_institute_users_id);


--
-- Name: event_log_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY event_log
    ADD CONSTRAINT event_log_pkey PRIMARY KEY (event_id);


--
-- Name: finance_app_status_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY finance_app_status
    ADD CONSTRAINT finance_app_status_pkey PRIMARY KEY (id);


--
-- Name: finance_approve_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY finance_approve_details
    ADD CONSTRAINT finance_approve_details_pkey PRIMARY KEY (id);


--
-- Name: finance_approve_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY finance_approve_history
    ADD CONSTRAINT finance_approve_history_pkey PRIMARY KEY (id);


--
-- Name: finance_branch_employee_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY finance_branch_employee
    ADD CONSTRAINT finance_branch_employee_pkey PRIMARY KEY (id);


--
-- Name: finance_branch_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY finance_branch
    ADD CONSTRAINT finance_branch_pkey PRIMARY KEY (id);


--
-- Name: finance_details_entity_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY finance_details_entity_backup
    ADD CONSTRAINT finance_details_entity_pkey PRIMARY KEY (id);


--
-- Name: finance_details_entity_pkeyn; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY finance_details_entityn
    ADD CONSTRAINT finance_details_entity_pkeyn PRIMARY KEY (id);


--
-- Name: finance_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY finance_history
    ADD CONSTRAINT finance_history_pkey PRIMARY KEY (id);


--
-- Name: finance_token_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY finance_token
    ADD CONSTRAINT finance_token_pkey PRIMARY KEY (id);


--
-- Name: finance_yard_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY finance_yard
    ADD CONSTRAINT finance_yard_pkey PRIMARY KEY (id);


--
-- Name: financer_fresh_contact_details_pkey; Type: CONSTRAINT; Schema: public; Owner: rtamigprod; Tablespace: 
--

ALTER TABLE ONLY financer_fresh_contact_details
    ADD CONSTRAINT financer_fresh_contact_details_pkey PRIMARY KEY (id);


--
-- Name: financer_fresh_rc_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY financer_fresh_rc
    ADD CONSTRAINT financer_fresh_rc_pkey PRIMARY KEY (id);


--
-- Name: financer_master_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY financer_master
    ADD CONSTRAINT financer_master_pkey PRIMARY KEY (id);


--
-- Name: financer_seized_vehicles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY financer_seized_vehicles
    ADD CONSTRAINT financer_seized_vehicles_pkey PRIMARY KEY (financer_siezed_vehicles_id);


--
-- Name: fitness_cert_details_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY fitness_cert_details_history
    ADD CONSTRAINT fitness_cert_details_history_pkey PRIMARY KEY (fitness_history_id);


--
-- Name: fitness_cert_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY fitness_cert_details
    ADD CONSTRAINT fitness_cert_details_pkey PRIMARY KEY (fitness_id);


--
-- Name: fitness_fee_detail_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY fitness_fee_detail_history
    ADD CONSTRAINT fitness_fee_detail_history_pkey PRIMARY KEY (fitness_fee_history_id);


--
-- Name: fitness_fee_detail_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY fitness_fee_detail
    ADD CONSTRAINT fitness_fee_detail_pkey PRIMARY KEY (fitness_fee_id);


--
-- Name: fitness_fee_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY fitness_fee_details
    ADD CONSTRAINT fitness_fee_details_pkey PRIMARY KEY (fitness_fee_id);


--
-- Name: green_tax_details_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY green_tax_details_history
    ADD CONSTRAINT green_tax_details_history_pkey PRIMARY KEY (green_dtl_hist_id);


--
-- Name: green_tax_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY green_tax_details
    ADD CONSTRAINT green_tax_details_pkey PRIMARY KEY (green_tax_dtl_id);


--
-- Name: hazardous_vehicle_driving_institute_users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY hazardous_vehicle_driving_institute_users
    ADD CONSTRAINT hazardous_vehicle_driving_institute_users_pkey PRIMARY KEY (hazardous_vehicle_driving_institute_users_id);


--
-- Name: home_tax_pkey; Type: CONSTRAINT; Schema: public; Owner: rtamigprod; Tablespace: 
--

ALTER TABLE ONLY home_tax
    ADD CONSTRAINT home_tax_pkey PRIMARY KEY (home_tax_id);


--
-- Name: hsrp_detail_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY hsrp_detail_history
    ADD CONSTRAINT hsrp_detail_history_pkey PRIMARY KEY (hsrp_detail_hist_id);


--
-- Name: hsrp_detail_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY hsrp_detail
    ADD CONSTRAINT hsrp_detail_pkey PRIMARY KEY (hsrp_detail_id);


--
-- Name: hsrp_fee_details_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY hsrp_fee_details_history
    ADD CONSTRAINT hsrp_fee_details_history_pkey PRIMARY KEY (hsrp_hist_id);


--
-- Name: hsrp_fee_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY hsrp_fee_details
    ADD CONSTRAINT hsrp_fee_details_pkey PRIMARY KEY (hsrp_fee_dtl_id);


--
-- Name: hsrp_transaction_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY hsrp_transaction
    ADD CONSTRAINT hsrp_transaction_pkey PRIMARY KEY (hsrp_transaction_id);


--
-- Name: insurance_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY insurance_details
    ADD CONSTRAINT insurance_details_pkey PRIMARY KEY (insurance_dtl_id);


--
-- Name: insurance_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY insurance_type
    ADD CONSTRAINT insurance_type_pkey PRIMARY KEY (insurance_type_id);


--
-- Name: late_fee_details_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY late_fee_details_history
    ADD CONSTRAINT late_fee_details_history_pkey PRIMARY KEY (late_fee_hist_id);


--
-- Name: late_fee_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY late_fee_details
    ADD CONSTRAINT late_fee_details_pkey PRIMARY KEY (late_fee_id);


--
-- Name: learners_permit_dtls_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY learners_permit_dtls_history
    ADD CONSTRAINT learners_permit_dtls_history_pkey PRIMARY KEY (learners_permit_hist_id);


--
-- Name: learners_permit_dtls_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY learners_permit_dtls
    ADD CONSTRAINT learners_permit_dtls_pkey PRIMARY KEY (licence_holder_id, llr_sequence_id, llr_vehicle_class_code);


--
-- Name: legal_hier_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY legal_hier_details
    ADD CONSTRAINT legal_hier_details_pkey PRIMARY KEY (legal_hier_id);


--
-- Name: licence_attachment_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY licence_attachment_details
    ADD CONSTRAINT licence_attachment_details_pkey PRIMARY KEY (attachment_detail_id);


--
-- Name: licence_print_request_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY licence_print_request
    ADD CONSTRAINT licence_print_request_pkey PRIMARY KEY (print_request_id);


--
-- Name: license_holder_dtls_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY license_holder_dtls
    ADD CONSTRAINT license_holder_dtls_pkey PRIMARY KEY (licence_holder_id);


--
-- Name: license_holder_txns_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY license_holder_txns
    ADD CONSTRAINT license_holder_txns_pkey PRIMARY KEY (licence_holder_id, sequence_id);


--
-- Name: license_identities_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY license_identities_details
    ADD CONSTRAINT license_identities_details_pkey PRIMARY KEY (license_identity_id);


--
-- Name: license_idp_dtls_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY license_idp_dtls
    ADD CONSTRAINT license_idp_dtls_pkey PRIMARY KEY (idp_sequence_id, licence_holder_id);


--
-- Name: license_vehicle_class_ref_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY license_vehicle_class_ref
    ADD CONSTRAINT license_vehicle_class_ref_pkey PRIMARY KEY (vehicle_class);


--
-- Name: life_tax_detail_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY life_tax_details
    ADD CONSTRAINT life_tax_detail_pkey PRIMARY KEY (life_tax_dtl_id);


--
-- Name: life_tax_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY life_tax_details_dup
    ADD CONSTRAINT life_tax_details_pkey PRIMARY KEY (life_tax_dtl_id);


--
-- Name: login_track_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY login_track
    ADD CONSTRAINT login_track_pkey PRIMARY KEY (login_track_id);


--
-- Name: maker_master_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY maker_master
    ADD CONSTRAINT maker_master_pkey PRIMARY KEY (maker_id);


--
-- Name: mandal_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY mandal
    ADD CONSTRAINT mandal_pkey PRIMARY KEY (mandal_id);


--
-- Name: medical_practitioner_users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY medical_practitioner_users
    ADD CONSTRAINT medical_practitioner_users_pkey PRIMARY KEY (medical_practitioner_user_id);


--
-- Name: model_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY model_details
    ADD CONSTRAINT model_details_pkey PRIMARY KEY (model_details_id);


--
-- Name: neigh_district_mapping_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY neigh_district_mapping
    ADD CONSTRAINT neigh_district_mapping_pkey PRIMARY KEY (district_id);


--
-- Name: new_vehicle_rc_migration_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY new_vehicle_rc_migration
    ADD CONSTRAINT new_vehicle_rc_migration_pkey PRIMARY KEY (vehicle_rc_id);


--
-- Name: ownership_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ownership_type
    ADD CONSTRAINT ownership_type_pkey PRIMARY KEY (ownership_type_id);


--
-- Name: p_address_not_matched_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY p_address_not_matched
    ADD CONSTRAINT p_address_not_matched_pkey PRIMARY KEY (p_address_not_matched_id);


--
-- Name: periodic_tax_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY periodic_tax_details_dup
    ADD CONSTRAINT periodic_tax_details_pkey PRIMARY KEY (tax_dtl_id);


--
-- Name: periodic_tax_details_primarykey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY periodic_tax_details
    ADD CONSTRAINT periodic_tax_details_primarykey PRIMARY KEY (tax_dtl_id);


--
-- Name: permanent_address_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY permanent_address_history
    ADD CONSTRAINT permanent_address_history_pkey PRIMARY KEY (p_address_history_id);


--
-- Name: permanent_address_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY permanent_address
    ADD CONSTRAINT permanent_address_pkey PRIMARY KEY (p_address_id);


--
-- Name: permit_allowed_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY permit_allowed
    ADD CONSTRAINT permit_allowed_pkey PRIMARY KEY (permit_allowed_id);


--
-- Name: permit_auth_card_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY permit_auth_card_details
    ADD CONSTRAINT permit_auth_card_details_pkey PRIMARY KEY (permit_auth_card_details_id);


--
-- Name: permit_conditions_master_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY permit_conditions_master
    ADD CONSTRAINT permit_conditions_master_pkey PRIMARY KEY (permit_cond_id);


--
-- Name: permit_details_mapping_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY permit_details_mapping
    ADD CONSTRAINT permit_details_mapping_pkey PRIMARY KEY (permit_details_id);


--
-- Name: permit_fee_detail_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY permit_fee_detail
    ADD CONSTRAINT permit_fee_detail_pkey PRIMARY KEY (permit_fee_id);


--
-- Name: permit_goods_master_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY permit_goods_master
    ADD CONSTRAINT permit_goods_master_pkey PRIMARY KEY (permit_goods_id);


--
-- Name: permit_header_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY permit_header
    ADD CONSTRAINT permit_header_pkey PRIMARY KEY (permit_header_id);


--
-- Name: permit_route_conditions_master_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY permit_route_conditions_master
    ADD CONSTRAINT permit_route_conditions_master_pkey PRIMARY KEY (permit_route_id);


--
-- Name: permit_susp_dtls_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY permit_susp_dtls
    ADD CONSTRAINT permit_susp_dtls_pkey PRIMARY KEY (permit_susp_dtls_id);


--
-- Name: permit_type_master_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY permit_type_master
    ADD CONSTRAINT permit_type_master_pkey PRIMARY KEY (permit_type_id);


--
-- Name: permit_vehicle_class_mapping_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY permit_vehicle_class_mapping
    ADD CONSTRAINT permit_vehicle_class_mapping_pkey PRIMARY KEY (permit_vclass_mapping_id);


--
-- Name: post_office_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY post_office
    ADD CONSTRAINT post_office_pkey PRIMARY KEY (post_office_id);


--
-- Name: pr_series_master_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pr_series_master
    ADD CONSTRAINT pr_series_master_pkey PRIMARY KEY (pr_series_master_id);


--
-- Name: primary_temp_permit_mapping_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY primary_temp_permit_mapping
    ADD CONSTRAINT primary_temp_permit_mapping_pkey PRIMARY KEY (primary_temp_permit_mapping_id);


--
-- Name: puc_users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY puc_users
    ADD CONSTRAINT puc_users_pkey PRIMARY KEY (puc_user_id);


--
-- Name: qualification_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY qualification
    ADD CONSTRAINT qualification_pkey PRIMARY KEY (qualification_id);


--
-- Name: rc_card_employee_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rc_card_employee
    ADD CONSTRAINT rc_card_employee_pkey PRIMARY KEY (rccard_employee_id);


--
-- Name: rc_lock_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rc_lock
    ADD CONSTRAINT rc_lock_pkey PRIMARY KEY (rc_lock_id);


--
-- Name: recom_lttr_issued_dtls_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY recom_lttr_issued_dtls
    ADD CONSTRAINT recom_lttr_issued_dtls_pkey PRIMARY KEY (recom_lttr_issued_dtls_id);


--
-- Name: reg_fee_detail_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY reg_fee_detail_history
    ADD CONSTRAINT reg_fee_detail_history_pkey PRIMARY KEY (reg_fee_hist_id);


--
-- Name: reg_fee_detail_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY reg_fee_detail
    ADD CONSTRAINT reg_fee_detail_pkey PRIMARY KEY (reg_fee_dtlid);


--
-- Name: reg_fee_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY reg_fee_details
    ADD CONSTRAINT reg_fee_details_pkey PRIMARY KEY (reg_fee_dtl_id);


--
-- Name: reg_fee_vehicle_uniq; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY reg_fee_details
    ADD CONSTRAINT reg_fee_vehicle_uniq UNIQUE (vehicle_rc_id);


--
-- Name: registration_category_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY registration_category
    ADD CONSTRAINT registration_category_pkey PRIMARY KEY (registration_category_id);


--
-- Name: rejection_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rejection_history
    ADD CONSTRAINT rejection_history_pkey PRIMARY KEY (rejection_history_id);


--
-- Name: reset_password_log_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY reset_password_log
    ADD CONSTRAINT reset_password_log_pkey PRIMARY KEY (reset_pwd_id);


--
-- Name: route_details_master_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY route_details_master
    ADD CONSTRAINT route_details_master_pkey PRIMARY KEY (stage_no);


--
-- Name: route_master_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY route_master
    ADD CONSTRAINT route_master_pkey PRIMARY KEY (route_serial);


--
-- Name: rta_employee_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rta_employee
    ADD CONSTRAINT rta_employee_pkey PRIMARY KEY (rta_employee_id);


--
-- Name: rta_mandal_mapping_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rta_mandal_mapping
    ADD CONSTRAINT rta_mandal_mapping_pkey PRIMARY KEY (mandal_id);


--
-- Name: rta_office_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rta_office
    ADD CONSTRAINT rta_office_pkey PRIMARY KEY (rta_office_id);


--
-- Name: rta_offices_serial_no_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rta_offices_serial_no
    ADD CONSTRAINT rta_offices_serial_no_pkey PRIMARY KEY (rta_offices_serial_no_id);


--
-- Name: rta_service_charge_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rta_service_charge
    ADD CONSTRAINT rta_service_charge_pkey PRIMARY KEY (rta_service_charge_id);


--
-- Name: rta_vehicle_tax_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rta_vehicle_tax
    ADD CONSTRAINT rta_vehicle_tax_pkey PRIMARY KEY (rta_vehicle_tax_id);


--
-- Name: sbi_ddo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sbi_ddo
    ADD CONSTRAINT sbi_ddo_pkey PRIMARY KEY (sbi_ddo_id);


--
-- Name: sbi_distribution_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sbi_distribution
    ADD CONSTRAINT sbi_distribution_pkey PRIMARY KEY (sbi_dist_id);


--
-- Name: second_vehicle_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY second_vehicle_details
    ADD CONSTRAINT second_vehicle_details_pkey PRIMARY KEY (second_vehicle_id);


--
-- Name: special_number_fee_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY special_number_fee_details
    ADD CONSTRAINT special_number_fee_details_pkey PRIMARY KEY (special_number_fee_id);


--
-- Name: special_number_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY special_number
    ADD CONSTRAINT special_number_pkey PRIMARY KEY (special_no_id);


--
-- Name: special_number_rta_office_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY special_number_rta_office
    ADD CONSTRAINT special_number_rta_office_pkey PRIMARY KEY (special_no_rta_id);


--
-- Name: state_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY state
    ADD CONSTRAINT state_pkey PRIMARY KEY (state_id);


--
-- Name: status_reference_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY status_reference
    ADD CONSTRAINT status_reference_pkey PRIMARY KEY (status_code);


--
-- Name: stoppage_application_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY stoppage_application
    ADD CONSTRAINT stoppage_application_pkey PRIMARY KEY (stoppage_app_id);


--
-- Name: stoppage_fee_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY stoppage_fee
    ADD CONSTRAINT stoppage_fee_pkey PRIMARY KEY (stoppage_fee_id);


--
-- Name: stoppage_inspection_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY stoppage_inspection
    ADD CONSTRAINT stoppage_inspection_pkey PRIMARY KEY (stoppage_inspection_id);


--
-- Name: stoppage_revocation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY stoppage_revocation
    ADD CONSTRAINT stoppage_revocation_pkey PRIMARY KEY (stoppage_revocation_id);


--
-- Name: susp_drivers_license_dtls_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY susp_drivers_license_dtls
    ADD CONSTRAINT susp_drivers_license_dtls_pkey PRIMARY KEY (dl_sequence_id, licence_holder_id);


--
-- Name: suspended_rc_no_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY suspended_rc_no
    ADD CONSTRAINT suspended_rc_no_pkey PRIMARY KEY (suspended_rc_no_id);


--
-- Name: tax_detail_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tax_detail_history
    ADD CONSTRAINT tax_detail_history_pkey PRIMARY KEY (tax_hist_id);


--
-- Name: tax_detail_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tax_detail
    ADD CONSTRAINT tax_detail_pkey PRIMARY KEY (tax_dtl_id);


--
-- Name: tax_master_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tax_master
    ADD CONSTRAINT tax_master_pkey PRIMARY KEY (tax_master_id);


--
-- Name: tax_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tax_type
    ADD CONSTRAINT tax_type_pkey PRIMARY KEY (tax_type_id);


--
-- Name: tmp_new_vrc_mig_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tmp_new_vrc_mig
    ADD CONSTRAINT tmp_new_vrc_mig_pkey PRIMARY KEY (vehicle_rc_id);


--
-- Name: tmp_new_vrc_mig_trpr_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tmp_new_vrc_mig_trpr
    ADD CONSTRAINT tmp_new_vrc_mig_trpr_pkey PRIMARY KEY (vehicle_rc_id);


--
-- Name: tr_series_master_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tr_series_master
    ADD CONSTRAINT tr_series_master_pkey PRIMARY KEY (tr_series_master_id);


--
-- Name: transaction_detail_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY transaction_detail
    ADD CONSTRAINT transaction_detail_pkey PRIMARY KEY (transaction_id);


--
-- Name: transaction_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY transaction_history
    ADD CONSTRAINT transaction_history_pkey PRIMARY KEY (trans_hist_id);


--
-- Name: uk_1597djv4wu9yprsjj5p4t2jp1; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY second_vehicle_details
    ADD CONSTRAINT uk_1597djv4wu9yprsjj5p4t2jp1 UNIQUE (vehicle_rc_id);


--
-- Name: uk_197dj5fbnkirckggcaf6umyff; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_noc_address
    ADD CONSTRAINT uk_197dj5fbnkirckggcaf6umyff UNIQUE (noc_address_code);


--
-- Name: uk_1krc9fusyokvd78i1lh41ordl; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY insurance_details
    ADD CONSTRAINT uk_1krc9fusyokvd78i1lh41ordl UNIQUE (vehicle_rc_id);


--
-- Name: uk_2rgq3sj0wtebn7174fgfo6fpkj; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_bharat_stage_duplicates
    ADD CONSTRAINT uk_2rgq3sj0wtebn7174fgfo6fpkj UNIQUE (chassis_number);


--
-- Name: uk_2rgq3sj0wtebn7174fgfo6fpkk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_bharat_stage_backup
    ADD CONSTRAINT uk_2rgq3sj0wtebn7174fgfo6fpkk UNIQUE (chassis_number);


--
-- Name: uk_3yw3ok219v28srfiscxroyccv; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY license_holder_dtls
    ADD CONSTRAINT uk_3yw3ok219v28srfiscxroyccv UNIQUE (aadhaar_no);


--
-- Name: uk_402f2p6u7d7gstyr2pqi8exu6; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vcr_details
    ADD CONSTRAINT uk_402f2p6u7d7gstyr2pqi8exu6 UNIQUE (vcr_number);


--
-- Name: uk_4am9d3dy75kh0hkx624l9ns8i; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY insurance_details
    ADD CONSTRAINT uk_4am9d3dy75kh0hkx624l9ns8i UNIQUE (vehicle_dtl_id);


--
-- Name: uk_4v26iqgeb0a3nuglmll7asot5; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY hsrp_fee_details_history
    ADD CONSTRAINT uk_4v26iqgeb0a3nuglmll7asot5 UNIQUE (vehicle_rc_id);


--
-- Name: uk_4vvkuv43wb5d0uxwsoc9mxu7t; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cfx_txn_dtl
    ADD CONSTRAINT uk_4vvkuv43wb5d0uxwsoc9mxu7t UNIQUE (policy_number);


--
-- Name: uk_5j3t10v1eoh56patywdu67ces; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cfx_txn_dtl
    ADD CONSTRAINT uk_5j3t10v1eoh56patywdu67ces UNIQUE (pg_payment_token);


--
-- Name: uk_5oi5dmx7cbyq3r097og5ulj3a; Type: CONSTRAINT; Schema: public; Owner: rtamigprod; Tablespace: 
--

ALTER TABLE ONLY vehicle_rc_dup
    ADD CONSTRAINT uk_5oi5dmx7cbyq3r097og5ulj3a UNIQUE (pr_number);


--
-- Name: uk_5s4ptnuqtd24d4p9au2rv53qm; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY country
    ADD CONSTRAINT uk_5s4ptnuqtd24d4p9au2rv53qm UNIQUE (code);


--
-- Name: uk_7ava2vvsdlafw7ca6y5j2a6q8; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY district
    ADD CONSTRAINT uk_7ava2vvsdlafw7ca6y5j2a6q8 UNIQUE (code);


--
-- Name: uk_7xchrkt97cr4cehts05o652mo; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY license_identities_details
    ADD CONSTRAINT uk_7xchrkt97cr4cehts05o652mo UNIQUE (dl_number);


--
-- Name: uk_b9c8axcyn70uyyj2093vvml72; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_billing_details
    ADD CONSTRAINT uk_b9c8axcyn70uyyj2093vvml72 UNIQUE (vehicle_dtl_id);


--
-- Name: uk_cle410yhbs58ckxt5lar40t2j; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY qualification
    ADD CONSTRAINT uk_cle410yhbs58ckxt5lar40t2j UNIQUE (code);


--
-- Name: uk_ct2yvxl4qga9mbyangjw10nob; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY reg_fee_details
    ADD CONSTRAINT uk_ct2yvxl4qga9mbyangjw10nob UNIQUE (vehicle_rc_id);


--
-- Name: uk_d08dcva21dpgsjou2xgd71f8g; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vahan_master
    ADD CONSTRAINT uk_d08dcva21dpgsjou2xgd71f8g UNIQUE (chassis_no);


--
-- Name: uk_divuymnbux5icwdultcf4aae9; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY designation
    ADD CONSTRAINT uk_divuymnbux5icwdultcf4aae9 UNIQUE (code);


--
-- Name: uk_dnac3ja0ysln3ysqoqp1v79hd; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cfx_txn_dtl
    ADD CONSTRAINT uk_dnac3ja0ysln3ysqoqp1v79hd UNIQUE (cfx_payment_id);


--
-- Name: uk_e06ltudhcvyn92hbwma17dca5; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_details_dup
    ADD CONSTRAINT uk_e06ltudhcvyn92hbwma17dca5 UNIQUE (chassis_no);


--
-- Name: uk_f67c38qxb6arierb9631vmwf1; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY permit_type_master
    ADD CONSTRAINT uk_f67c38qxb6arierb9631vmwf1 UNIQUE (code);


--
-- Name: uk_fd96n7xcjspmp1rwn8wwdj3jk; Type: CONSTRAINT; Schema: public; Owner: rtamigprod; Tablespace: 
--

ALTER TABLE ONLY vehicle_rc_dup
    ADD CONSTRAINT uk_fd96n7xcjspmp1rwn8wwdj3jk UNIQUE (tr_number);


--
-- Name: uk_fq939g1lo44ihkuhwajg1hw56; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cfx_txn_dtl
    ADD CONSTRAINT uk_fq939g1lo44ihkuhwajg1hw56 UNIQUE (cfx_txn_id);


--
-- Name: uk_gh9ikb0oamjcpelsweit21jt0; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY license_identities_details
    ADD CONSTRAINT uk_gh9ikb0oamjcpelsweit21jt0 UNIQUE (passport_number);


--
-- Name: uk_gundrdk47kofm0n6a89qvqye; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_details_dup
    ADD CONSTRAINT uk_gundrdk47kofm0n6a89qvqye UNIQUE (engine_no);


--
-- Name: uk_ikjcuvji6qxpj3bd7tjx0aoyy; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY late_fee_details
    ADD CONSTRAINT uk_ikjcuvji6qxpj3bd7tjx0aoyy UNIQUE (vehicle_rc_id);


--
-- Name: uk_jyngahija4xi1be6hu9irw28d; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_pr_release
    ADD CONSTRAINT uk_jyngahija4xi1be6hu9irw28d UNIQUE (pr_number);


--
-- Name: uk_k8d0f2n7n88w1a16yhua64onx; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT uk_k8d0f2n7n88w1a16yhua64onx UNIQUE (user_name);


--
-- Name: uk_kkoi99xhsb4ol4jcpsi91003w; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY permanent_address
    ADD CONSTRAINT uk_kkoi99xhsb4ol4jcpsi91003w UNIQUE (user_id);


--
-- Name: uk_kneh7omt6kspigrbfe10rjgnx; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY finance_token
    ADD CONSTRAINT uk_kneh7omt6kspigrbfe10rjgnx UNIQUE (token_id);


--
-- Name: uk_nmit0t7rediqi0mjkkfbevdhd; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY mandal
    ADD CONSTRAINT uk_nmit0t7rediqi0mjkkfbevdhd UNIQUE (code);


--
-- Name: uk_ossjp49iuycumvj08o9hm2lja; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY address_proof_type
    ADD CONSTRAINT uk_ossjp49iuycumvj08o9hm2lja UNIQUE (code);


--
-- Name: uk_p0cdb3brs3dx9b7sk49natxrn; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY registration_category
    ADD CONSTRAINT uk_p0cdb3brs3dx9b7sk49natxrn UNIQUE (code);


--
-- Name: uk_p58mnjr2recwy0s4ey2eglfae; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cfx_txn_dtl
    ADD CONSTRAINT uk_p58mnjr2recwy0s4ey2eglfae UNIQUE (rta_cfx_txn_id);


--
-- Name: uk_p662kh6f29ac0282m8pmmgpt; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY fitness_fee_details
    ADD CONSTRAINT uk_p662kh6f29ac0282m8pmmgpt UNIQUE (vehicle_rc_id);


--
-- Name: uk_pexforeg2sel6ehiphmhetgjj; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tax_type
    ADD CONSTRAINT uk_pexforeg2sel6ehiphmhetgjj UNIQUE (code);


--
-- Name: uk_q5vsym1xplnc8bim644gnfc7e; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY post_office
    ADD CONSTRAINT uk_q5vsym1xplnc8bim644gnfc7e UNIQUE (pin_code);


--
-- Name: uk_q6p476dmchysm8hdn46fsssyk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY hsrp_fee_details
    ADD CONSTRAINT uk_q6p476dmchysm8hdn46fsssyk UNIQUE (vehicle_rc_id);


--
-- Name: uk_qriptahaanot27p2oem9uydk8; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ownership_type
    ADD CONSTRAINT uk_qriptahaanot27p2oem9uydk8 UNIQUE (code);


--
-- Name: uk_r9dwheh83k24lrelyc8ptg5bi; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cess_fee_details
    ADD CONSTRAINT uk_r9dwheh83k24lrelyc8ptg5bi UNIQUE (vehicle_rc_id);


--
-- Name: uk_sleimnwe013aiopqeyp6qspg0; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY state
    ADD CONSTRAINT uk_sleimnwe013aiopqeyp6qspg0 UNIQUE (code);


--
-- Name: uk_t4mxfivhdmayrvr261f1f19n0; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_details_dup
    ADD CONSTRAINT uk_t4mxfivhdmayrvr261f1f19n0 UNIQUE (vehicle_rc_id);


--
-- Name: uk_ti3gad11iiqobfj971byfrcm8; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY insurance_type
    ADD CONSTRAINT uk_ti3gad11iiqobfj971byfrcm8 UNIQUE (code);


--
-- Name: user_attachment_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY user_attachment_details
    ADD CONSTRAINT user_attachment_details_pkey PRIMARY KEY (attachment_detail_id);


--
-- Name: user_transfer_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY user_transfer_history
    ADD CONSTRAINT user_transfer_history_pkey PRIMARY KEY (user_transfer_id);


--
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


--
-- Name: vahan_master_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vahan_master
    ADD CONSTRAINT vahan_master_pkey PRIMARY KEY (id);


--
-- Name: vcr_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vcr_details
    ADD CONSTRAINT vcr_details_pkey PRIMARY KEY (vcr_id);


--
-- Name: vcr_offense_logs_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vcr_offense_logs
    ADD CONSTRAINT vcr_offense_logs_pkey PRIMARY KEY (id);


--
-- Name: vcr_payment_logs_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vcr_payment_logs
    ADD CONSTRAINT vcr_payment_logs_pkey PRIMARY KEY (id);


--
-- Name: vcr_transaction_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vcr_transaction_details
    ADD CONSTRAINT vcr_transaction_details_pkey PRIMARY KEY (vcr_transaction_id);


--
-- Name: vehicle_alteration_attachment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_alteration_attachment
    ADD CONSTRAINT vehicle_alteration_attachment_pkey PRIMARY KEY (vehicle_alter_attachment_id);


--
-- Name: vehicle_alteration_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_alteration_details
    ADD CONSTRAINT vehicle_alteration_details_pkey PRIMARY KEY (alteration_details_id);


--
-- Name: vehicle_bharat_stage_backup_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_bharat_stage_backup
    ADD CONSTRAINT vehicle_bharat_stage_backup_pkey PRIMARY KEY (id);


--
-- Name: vehicle_bharat_stage_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_bharat_stage
    ADD CONSTRAINT vehicle_bharat_stage_pkey PRIMARY KEY (id);


--
-- Name: vehicle_bharat_stage_unique_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_bharat_stage_duplicates
    ADD CONSTRAINT vehicle_bharat_stage_unique_pkey PRIMARY KEY (id);


--
-- Name: vehicle_billing_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_billing_details
    ADD CONSTRAINT vehicle_billing_details_pkey PRIMARY KEY (vehicle_billing_id);


--
-- Name: vehicle_class_desc_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_class_desc_bck
    ADD CONSTRAINT vehicle_class_desc_pkey PRIMARY KEY (vehicle_class_desc_id);


--
-- Name: vehicle_class_desc_pkey1; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_class_desc
    ADD CONSTRAINT vehicle_class_desc_pkey1 PRIMARY KEY (vehicle_class_desc_id);


--
-- Name: vehicle_class_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_class_bck
    ADD CONSTRAINT vehicle_class_pkey PRIMARY KEY (vehicle_class_id);


--
-- Name: vehicle_class_pkey1; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_class
    ADD CONSTRAINT vehicle_class_pkey1 PRIMARY KEY (vehicle_class_id);


--
-- Name: vehicle_current_tax_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_current_tax_details_dup
    ADD CONSTRAINT vehicle_current_tax_details_pkey PRIMARY KEY (vehicle_tax_dtl_id);


--
-- Name: vehicle_details_chassis_no_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_details
    ADD CONSTRAINT vehicle_details_chassis_no_key UNIQUE (chassis_no);


--
-- Name: vehicle_details_engine_no_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_details
    ADD CONSTRAINT vehicle_details_engine_no_key UNIQUE (engine_no);


--
-- Name: vehicle_details_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_details_history
    ADD CONSTRAINT vehicle_details_history_pkey PRIMARY KEY (vehicle_dtl_history_id);


--
-- Name: vehicle_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_details_dup
    ADD CONSTRAINT vehicle_details_pkey PRIMARY KEY (vehicle_dtl_id);


--
-- Name: vehicle_details_primarykey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_details
    ADD CONSTRAINT vehicle_details_primarykey PRIMARY KEY (vehicle_dtl_id);


--
-- Name: vehicle_details_vehicle_rc_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_details
    ADD CONSTRAINT vehicle_details_vehicle_rc_id_key UNIQUE (vehicle_rc_id);


--
-- Name: vehicle_duplicate_registration_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_duplicate_registration
    ADD CONSTRAINT vehicle_duplicate_registration_pkey PRIMARY KEY (vehicle_dup_reg_id);


--
-- Name: vehicle_finance_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_finance_details
    ADD CONSTRAINT vehicle_finance_details_pkey PRIMARY KEY (id);


--
-- Name: vehicle_noc_address_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_noc_address
    ADD CONSTRAINT vehicle_noc_address_pkey PRIMARY KEY (nco_address_id);


--
-- Name: vehicle_noc_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_noc_history
    ADD CONSTRAINT vehicle_noc_history_pkey PRIMARY KEY (noc_history_id);


--
-- Name: vehicle_noc_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_noc
    ADD CONSTRAINT vehicle_noc_pkey PRIMARY KEY (noc_id);


--
-- Name: vehicle_pr_release_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_pr_release
    ADD CONSTRAINT vehicle_pr_release_pkey PRIMARY KEY (vehicle_pr_release_id);


--
-- Name: vehicle_puc_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_puc
    ADD CONSTRAINT vehicle_puc_pkey PRIMARY KEY (puc_id);


--
-- Name: vehicle_quarterly_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_quarterly_bck
    ADD CONSTRAINT vehicle_quarterly_pkey PRIMARY KEY (vehicle_quarterly_id);


--
-- Name: vehicle_quarterly_pkey1; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_quarterly
    ADD CONSTRAINT vehicle_quarterly_pkey1 PRIMARY KEY (vehicle_quarterly_id);


--
-- Name: vehicle_rc_change_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_rc_change_history
    ADD CONSTRAINT vehicle_rc_change_history_pkey PRIMARY KEY (vehicle_rc_change_history_id);


--
-- Name: vehicle_rc_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_rc_history
    ADD CONSTRAINT vehicle_rc_history_pkey PRIMARY KEY (vehicle_rc_history_id);


--
-- Name: vehicle_rc_id_uniq; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_current_tax_details_dup
    ADD CONSTRAINT vehicle_rc_id_uniq UNIQUE (vehicle_rc_id);


--
-- Name: vehicle_rc_pkey; Type: CONSTRAINT; Schema: public; Owner: rtamigprod; Tablespace: 
--

ALTER TABLE ONLY vehicle_rc_dup
    ADD CONSTRAINT vehicle_rc_pkey PRIMARY KEY (vehicle_rc_id);


--
-- Name: vehicle_rc_primarykey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_rc
    ADD CONSTRAINT vehicle_rc_primarykey PRIMARY KEY (vehicle_rc_id);


--
-- Name: vehicle_reg_fees_master_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_reg_fees_master
    ADD CONSTRAINT vehicle_reg_fees_master_pkey PRIMARY KEY (vehicle_reg_fees_master_id);


--
-- Name: vehicle_tax_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_current_tax_details
    ADD CONSTRAINT vehicle_tax_pkey PRIMARY KEY (vehicle_tax_dtl_id);


--
-- Name: vehicle_training_period_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_training_period
    ADD CONSTRAINT vehicle_training_period_pkey PRIMARY KEY (vehicle_class_code);


--
-- Name: vehicle_weight_master_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicle_weight_master
    ADD CONSTRAINT vehicle_weight_master_pkey PRIMARY KEY (vehicle_weight_master_id);


--
-- Name: IX_VRC_TRPRMVIUSERID; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX "IX_VRC_TRPRMVIUSERID" ON vehicle_rc USING btree (tr_status, pr_status, user_id, mvi_action_status) WITH (fillfactor='90');


--
-- Name: VC_tax_help_rc; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX "VC_tax_help_rc" ON vehicle_current_tax_details USING btree (vehicle_rc_id) WITH (fillfactor='90');


--
-- Name: aadhaar_master_uid_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX aadhaar_master_uid_idx ON aadhaar_master USING btree (uid);


--
-- Name: address_proof_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX address_proof_idx ON address_proof_type USING btree (status);


--
-- Name: address_type_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX address_type_idx ON address USING btree (type);


--
-- Name: address_user_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX address_user_idx ON address USING btree (user_id);


--
-- Name: attachment_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX attachment_idx ON attachment_details USING btree (vehicle_rc_id);


--
-- Name: attachment_status_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX attachment_status_index ON attachment_details USING btree (status);


--
-- Name: code_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE UNIQUE INDEX code_idx ON district USING btree (code);


--
-- Name: cust_corporate_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX cust_corporate_idx ON cust_corporate_details USING btree (vehicle_rc_id);


--
-- Name: cust_individual_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX cust_individual_idx ON cust_individual_details USING btree (vehicle_rc_id);


--
-- Name: dealer_user_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX dealer_user_idx ON dealer USING btree (user_id);


--
-- Name: doc_types_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX doc_types_idx ON attachment_details USING btree (doc_types_id);


--
-- Name: finance_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX finance_idx ON finance_details_entity_backup USING btree (vehicle_rc_id);


--
-- Name: finance_idxn; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX finance_idxn ON finance_details_entityn USING btree (vehicle_rc_id);


--
-- Name: finance_idxnew; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX finance_idxnew ON finance_details_entity USING btree (vehicle_rc_id);


--
-- Name: fitness_cet_vrcid; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX fitness_cet_vrcid ON fitness_cert_details USING btree (vehicle_rc_id) WITH (fillfactor='90');


--
-- Name: fitness_vrc; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX fitness_vrc ON fitness_cert_details USING btree (vehicle_regd_no) WITH (fillfactor='90');


--
-- Name: insurance_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX insurance_idx ON insurance_details USING btree (vehicle_rc_id);


--
-- Name: ix_regcat_categoryid; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX ix_regcat_categoryid ON registration_category USING btree (registration_category_id) WITH (fillfactor='90');


--
-- Name: ix_rtaooffice_id; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX ix_rtaooffice_id ON rta_office USING btree (rta_office_id) WITH (fillfactor='90');


--
-- Name: ix_vrcid; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX ix_vrcid ON periodic_tax_details USING btree (vehicle_rc_id) WITH (fillfactor='90');


--
-- Name: life_tax_dtl_id_idx2; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX life_tax_dtl_id_idx2 ON life_tax_details USING btree (life_tax_dtl_id);


--
-- Name: mandal_code_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE UNIQUE INDEX mandal_code_idx ON mandal USING btree (code);


--
-- Name: mandal_district_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX mandal_district_idx ON mandal USING btree (district_id);


--
-- Name: mandal_status_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX mandal_status_idx ON mandal USING btree (status);


--
-- Name: mig_rc_id_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX mig_rc_id_idx ON pr_mapping USING btree (mig_rc_id);


--
-- Name: office_code_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX office_code_idx ON rta_office USING btree (code);


--
-- Name: pr_number_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX pr_number_idx ON pr_mapping USING btree (pr_number);


--
-- Name: prod_rc_id_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX prod_rc_id_idx ON pr_mapping USING btree (prod_rc_id);


--
-- Name: rta_user_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX rta_user_idx ON rta_employee USING btree (user_id);


--
-- Name: service_charge_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX service_charge_idx ON rta_service_charge USING btree (vehicle_code);


--
-- Name: user_name_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE UNIQUE INDEX user_name_idx ON users USING btree (user_name);


--
-- Name: vcdt_nc_pr_no; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX vcdt_nc_pr_no ON vehicle_current_tax_details USING btree (vehicle_reg_no) WITH (fillfactor='90');


--
-- Name: veh_lif_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX veh_lif_idx ON life_tax_details USING btree (vehicle_rc_id);


--
-- Name: vehicle_chassis_idx; Type: INDEX; Schema: public; Owner: rtamigprod; Tablespace: 
--

CREATE INDEX vehicle_chassis_idx ON vehicle_rc_dup USING btree (chassis_no);


--
-- Name: vehicle_detail_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX vehicle_detail_idx ON vehicle_billing_details USING btree (vehicle_dtl_id);


--
-- Name: vehicle_details_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX vehicle_details_idx ON vehicle_details_dup USING btree (vehicle_rc_id);


--
-- Name: vehicle_details_rc_id_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX vehicle_details_rc_id_idx ON vehicle_details USING btree (vehicle_rc_id);


--
-- Name: vehicle_rc_chassis_no_idx1; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX vehicle_rc_chassis_no_idx1 ON vehicle_rc USING btree (chassis_no);


--
-- Name: vehicle_rc_history_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX vehicle_rc_history_idx ON vehicle_rc_history USING btree (vehicle_rc_id);


--
-- Name: vehicle_rc_history_user_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX vehicle_rc_history_user_idx ON vehicle_rc_history USING btree (user_id);


--
-- Name: vehicle_rc_pr_number_idx1; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX vehicle_rc_pr_number_idx1 ON vehicle_rc USING btree (pr_number);


--
-- Name: vehicle_rc_rta_office_idx; Type: INDEX; Schema: public; Owner: rtamigprod; Tablespace: 
--

CREATE INDEX vehicle_rc_rta_office_idx ON vehicle_rc_dup USING btree (rta_office_id);


--
-- Name: vehicle_rc_rta_office_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX vehicle_rc_rta_office_index ON vehicle_rc USING btree (rta_office_id);


--
-- Name: vehicle_rc_tr_number_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX vehicle_rc_tr_number_idx ON vehicle_rc USING btree (tr_number);


--
-- Name: vehicle_rc_user_idx; Type: INDEX; Schema: public; Owner: rtamigprod; Tablespace: 
--

CREATE INDEX vehicle_rc_user_idx ON vehicle_rc_dup USING btree (user_id);


--
-- Name: vehicle_rc_user_idx1; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX vehicle_rc_user_idx1 ON vehicle_rc USING btree (user_id);


--
-- Name: fk_19h1t3l5ucscrulfig43t52jg; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY finance_history
    ADD CONSTRAINT fk_19h1t3l5ucscrulfig43t52jg FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_1ht818vwg09adf05ke3qywx2y; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY mandal
    ADD CONSTRAINT fk_1ht818vwg09adf05ke3qywx2y FOREIGN KEY (district_id) REFERENCES district(district_id);


--
-- Name: fk_1krc9fusyokvd78i1lh41ordl; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY insurance_details
    ADD CONSTRAINT fk_1krc9fusyokvd78i1lh41ordl FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_1ks31gk97n3cxqmoad5mduihc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vcr_transaction_details
    ADD CONSTRAINT fk_1ks31gk97n3cxqmoad5mduihc FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_20hpw9kddhak1hbdvs1xyt7w3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY dealer_invoice
    ADD CONSTRAINT fk_20hpw9kddhak1hbdvs1xyt7w3 FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: fk_25e4hg4rhduljeppgof9g690; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tr_series_master
    ADD CONSTRAINT fk_25e4hg4rhduljeppgof9g690 FOREIGN KEY (district_id) REFERENCES district(district_id);


--
-- Name: fk_2lqfb17k8tsxgbfko07ivel7c; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cfx_txn_dtl
    ADD CONSTRAINT fk_2lqfb17k8tsxgbfko07ivel7c FOREIGN KEY (transaction_detail) REFERENCES transaction_detail(transaction_id);


--
-- Name: fk_2pykj6ouut1f1iq49i6ghg40e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY stoppage_fee
    ADD CONSTRAINT fk_2pykj6ouut1f1iq49i6ghg40e FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_31wx296g3q0m1bhmylafp9ih9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY drivers_license_dtls
    ADD CONSTRAINT fk_31wx296g3q0m1bhmylafp9ih9 FOREIGN KEY (rta_office_code) REFERENCES rta_office(rta_office_id);


--
-- Name: fk_36246bicmxwji1l8vknh5vidb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vehicle_alteration_attachment
    ADD CONSTRAINT fk_36246bicmxwji1l8vknh5vidb FOREIGN KEY (doc_master_id) REFERENCES doc_types_master(doc_type_id);


--
-- Name: fk_3g1dd7he0kykla27itkuly6km; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY finance_approve_details
    ADD CONSTRAINT fk_3g1dd7he0kykla27itkuly6km FOREIGN KEY (finance_other_service) REFERENCES finance_app_status(id);


--
-- Name: fk_3i1fmh4ef5p7ppaxaoruv3e3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY reg_fee_detail
    ADD CONSTRAINT fk_3i1fmh4ef5p7ppaxaoruv3e3 FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_3idvco9jrmaniifg9b811yhlm; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY finance_approve_history
    ADD CONSTRAINT fk_3idvco9jrmaniifg9b811yhlm FOREIGN KEY (approver_id) REFERENCES users(user_id);


--
-- Name: fk_3pxhfbeq0k1u0w5csv010nxgt; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY second_vehicle_details
    ADD CONSTRAINT fk_3pxhfbeq0k1u0w5csv010nxgt FOREIGN KEY (rejection_history_id) REFERENCES rejection_history(rejection_history_id);


--
-- Name: fk_3qk8tffkml3xat0yym8vcivgh; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vehicle_rc
    ADD CONSTRAINT fk_3qk8tffkml3xat0yym8vcivgh FOREIGN KEY (ao_user_id) REFERENCES users(user_id);


--
-- Name: fk_3yeh4yi8dlmyddn71y8g1q8ld; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vehicle_alteration_attachment
    ADD CONSTRAINT fk_3yeh4yi8dlmyddn71y8g1q8ld FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_43v83j6fpxp5ukxet4emtlfcr; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY reset_password_log
    ADD CONSTRAINT fk_43v83j6fpxp5ukxet4emtlfcr FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: fk_45brv9xeg9tctxbshpfxyj9ed; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY doc_permission
    ADD CONSTRAINT fk_45brv9xeg9tctxbshpfxyj9ed FOREIGN KEY (doc_types_id) REFERENCES doc_types_master(doc_type_id);


--
-- Name: fk_45igsf2smf1jhdafxo2fq1gh0; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY login_track
    ADD CONSTRAINT fk_45igsf2smf1jhdafxo2fq1gh0 FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: fk_48f7tgggfrtpjjwioa76ne4dl; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY learners_permit_dtls
    ADD CONSTRAINT fk_48f7tgggfrtpjjwioa76ne4dl FOREIGN KEY (ao_user_id) REFERENCES users(user_id);


--
-- Name: fk_4cwomokyy35a593mkwlwl8cvl; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY green_tax_details_history
    ADD CONSTRAINT fk_4cwomokyy35a593mkwlwl8cvl FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_4emv7u0thw0fnbklhw0nvbr31; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY model_details
    ADD CONSTRAINT fk_4emv7u0thw0fnbklhw0nvbr31 FOREIGN KEY (maker_master_id) REFERENCES maker_master(maker_id);


--
-- Name: fk_4j0h5ijg78pw329noui4ur8so; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY license_holder_txns
    ADD CONSTRAINT fk_4j0h5ijg78pw329noui4ur8so FOREIGN KEY (pres_addr_mandal_id) REFERENCES mandal(mandal_id);


--
-- Name: fk_4v26iqgeb0a3nuglmll7asot5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY hsrp_fee_details_history
    ADD CONSTRAINT fk_4v26iqgeb0a3nuglmll7asot5 FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_51grnx2m1sb6n8hrj7kmny4ob; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY financer_seized_vehicles
    ADD CONSTRAINT fk_51grnx2m1sb6n8hrj7kmny4ob FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: fk_583kb2tksg65nis95ipc9squh; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vehicle_noc_address
    ADD CONSTRAINT fk_583kb2tksg65nis95ipc9squh FOREIGN KEY (rta_office_id) REFERENCES rta_office(rta_office_id);


--
-- Name: fk_58qpc8g4u863xo4gtsrdaw70s; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY reg_fee_detail
    ADD CONSTRAINT fk_58qpc8g4u863xo4gtsrdaw70s FOREIGN KEY (permit_fee) REFERENCES permit_fee_detail(permit_fee_id);


--
-- Name: fk_5cn8joytwf4kjgst2ewa3oy4t; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY license_holder_txns
    ADD CONSTRAINT fk_5cn8joytwf4kjgst2ewa3oy4t FOREIGN KEY (pres_addr_district_id) REFERENCES district(district_id);


--
-- Name: fk_5fi9od3lqvvosvg2hq2299fg1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY address
    ADD CONSTRAINT fk_5fi9od3lqvvosvg2hq2299fg1 FOREIGN KEY (mandal_id) REFERENCES mandal(mandal_id);


--
-- Name: fk_5moccfqr0xmpedfs6mbu7p5wj; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY finance_approve_history
    ADD CONSTRAINT fk_5moccfqr0xmpedfs6mbu7p5wj FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_61d5ysw0sc4oals91mlxh6bnl; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vehicle_rc_change_history
    ADD CONSTRAINT fk_61d5ysw0sc4oals91mlxh6bnl FOREIGN KEY (rta_office_id) REFERENCES rta_office(rta_office_id);


--
-- Name: fk_628x9n6d5yaiwgr5ha4v8noku; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY attachment_details
    ADD CONSTRAINT fk_628x9n6d5yaiwgr5ha4v8noku FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_687e09d3oyf88a7iddtsaoxpy; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vehicle_pr_release
    ADD CONSTRAINT fk_687e09d3oyf88a7iddtsaoxpy FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_6xykywybhs2ecj3lxn2vavk0r; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vcr_offense_logs
    ADD CONSTRAINT fk_6xykywybhs2ecj3lxn2vavk0r FOREIGN KEY (vcr_offense_id) REFERENCES vcr_details(vcr_id);


--
-- Name: fk_73vb3kqlsnn7aqy2g4edffvq6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vehicle_rc_change_history
    ADD CONSTRAINT fk_73vb3kqlsnn7aqy2g4edffvq6 FOREIGN KEY (ao_user_id) REFERENCES users(user_id);


--
-- Name: fk_75f8nlsq315vjtyq63qr1oa1f; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pr_series_master
    ADD CONSTRAINT fk_75f8nlsq315vjtyq63qr1oa1f FOREIGN KEY (rta_office_id) REFERENCES rta_office(rta_office_id);


--
-- Name: fk_76xsrss8g7eddwm5wkmkbwww; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cust_corporate_details
    ADD CONSTRAINT fk_76xsrss8g7eddwm5wkmkbwww FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_7bhkyhxcu3s50uh7vhe8paame; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vehicle_noc
    ADD CONSTRAINT fk_7bhkyhxcu3s50uh7vhe8paame FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_7ewfd0b6jdp2db28na2pp4ski; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vehicle_rc
    ADD CONSTRAINT fk_7ewfd0b6jdp2db28na2pp4ski FOREIGN KEY (rto_user_id) REFERENCES users(user_id);


--
-- Name: fk_809xnpi4ga7y95151joxkqpt8; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vehicle_rc_change_history
    ADD CONSTRAINT fk_809xnpi4ga7y95151joxkqpt8 FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: fk_89m3dwbgg49xbjucwfmxv04v9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vehicle_rc
    ADD CONSTRAINT fk_89m3dwbgg49xbjucwfmxv04v9 FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: fk_8cn3fm4t4n15gd2s8d9sgwdp5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vehicle_rc_history
    ADD CONSTRAINT fk_8cn3fm4t4n15gd2s8d9sgwdp5 FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_8dp447kin22cwxeonoh0w5h79; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY reg_fee_detail
    ADD CONSTRAINT fk_8dp447kin22cwxeonoh0w5h79 FOREIGN KEY (fitness_fee) REFERENCES fitness_fee_detail(fitness_fee_id);


--
-- Name: fk_8gg8v6qbgj06e7rxp9q1a33it; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY licence_attachment_details
    ADD CONSTRAINT fk_8gg8v6qbgj06e7rxp9q1a33it FOREIGN KEY (doc_master_id) REFERENCES doc_types_master(doc_type_id);


--
-- Name: fk_8jny8o5t0vbg6uqmg73dmv5fj; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cust_individual_details_history
    ADD CONSTRAINT fk_8jny8o5t0vbg6uqmg73dmv5fj FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_8m0nts5r5qj7m50p9hgcs3qfm; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY license_idp_dtls
    ADD CONSTRAINT fk_8m0nts5r5qj7m50p9hgcs3qfm FOREIGN KEY (rta_office_code) REFERENCES rta_office(rta_office_id);


--
-- Name: fk_8p847cwu80ircwt6dii141vkw; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY finance_yard
    ADD CONSTRAINT fk_8p847cwu80ircwt6dii141vkw FOREIGN KEY (district_id) REFERENCES district(district_id);


--
-- Name: fk_8uameen7223da242yyhqe207t; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY attachment_details_history
    ADD CONSTRAINT fk_8uameen7223da242yyhqe207t FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_900iw5ahnophismuahfqlyw7q; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY insurance_details
    ADD CONSTRAINT fk_900iw5ahnophismuahfqlyw7q FOREIGN KEY (insurance_id) REFERENCES insurance_type(insurance_type_id);


--
-- Name: fk_94yruf4dxsnucod1qlseuuhrp; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY license_holder_dtls
    ADD CONSTRAINT fk_94yruf4dxsnucod1qlseuuhrp FOREIGN KEY (pres_addr_mandal_id) REFERENCES mandal(mandal_id);


--
-- Name: fk_980kdr53ntfaiegtatqswirqr; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rta_mandal_mapping
    ADD CONSTRAINT fk_980kdr53ntfaiegtatqswirqr FOREIGN KEY (mandal_id) REFERENCES mandal(mandal_id);


--
-- Name: fk_98gmnxaksyg8wv7g7pldbowb5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY finance_details_entity_backup
    ADD CONSTRAINT fk_98gmnxaksyg8wv7g7pldbowb5 FOREIGN KEY (district_entity) REFERENCES district(district_id);


--
-- Name: fk_98gmnxaksyg8wv7g7pldbowb5n; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY finance_details_entityn
    ADD CONSTRAINT fk_98gmnxaksyg8wv7g7pldbowb5n FOREIGN KEY (district_entity) REFERENCES district(district_id);


--
-- Name: fk_9bhomyl1aspg3b7xnj3hurp77; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cust_individual_details
    ADD CONSTRAINT fk_9bhomyl1aspg3b7xnj3hurp77 FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_9g3o856rq37qdu48bei1nmh07; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vehicle_alteration_details
    ADD CONSTRAINT fk_9g3o856rq37qdu48bei1nmh07 FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_9kgpx2d46cn53yq7nuphu2tj9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY periodic_tax_details
    ADD CONSTRAINT fk_9kgpx2d46cn53yq7nuphu2tj9 FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_9rgo5yvtm3qgx583f9wsxmhlw; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY finance_token
    ADD CONSTRAINT fk_9rgo5yvtm3qgx583f9wsxmhlw FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_9xhqtpa9340fropmgfuklkuka; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY dealer_invoice
    ADD CONSTRAINT fk_9xhqtpa9340fropmgfuklkuka FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_a4xf9xivbby41xnegimlcfs1g; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY licence_attachment_details
    ADD CONSTRAINT fk_a4xf9xivbby41xnegimlcfs1g FOREIGN KEY (licence_holder_id) REFERENCES license_holder_dtls(licence_holder_id);


--
-- Name: fk_ap5ms7rynso8jhy8n0k7vjkoe; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cfx_txn_dtl
    ADD CONSTRAINT fk_ap5ms7rynso8jhy8n0k7vjkoe FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_ar8yvfkqga0rddbl2n3tbo5ai; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rc_card_employee
    ADD CONSTRAINT fk_ar8yvfkqga0rddbl2n3tbo5ai FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: fk_as8rwv0sxu7k61c1ufa9uu0td; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY hsrp_transaction
    ADD CONSTRAINT fk_as8rwv0sxu7k61c1ufa9uu0td FOREIGN KEY (transaction_detail) REFERENCES transaction_detail(transaction_id);


--
-- Name: fk_au6wjnf0fljswtv5xwou7awd1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tax_detail
    ADD CONSTRAINT fk_au6wjnf0fljswtv5xwou7awd1 FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_b45y5owwn9m1wtq9xgq5fec6h; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vehicle_details_history
    ADD CONSTRAINT fk_b45y5owwn9m1wtq9xgq5fec6h FOREIGN KEY (tax_type_id) REFERENCES tax_type(tax_type_id);


--
-- Name: fk_b5nbsffd6b6iql5x8kh7qn2ur; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY green_tax_details
    ADD CONSTRAINT fk_b5nbsffd6b6iql5x8kh7qn2ur FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_bd8076uxvu8d1nxb75fphkfuc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY hazardous_vehicle_driving_institute_users
    ADD CONSTRAINT fk_bd8076uxvu8d1nxb75fphkfuc FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: fk_bkyx6otue8rmjyiejlfd72ype; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY puc_users
    ADD CONSTRAINT fk_bkyx6otue8rmjyiejlfd72ype FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: fk_c0ev6ls6r0tdpuanxvspat23k; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rta_employee
    ADD CONSTRAINT fk_c0ev6ls6r0tdpuanxvspat23k FOREIGN KEY (rta_office_id) REFERENCES rta_office(rta_office_id);


--
-- Name: fk_cjcy6i1pew56twcml3drfhef3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY learners_permit_dtls_history
    ADD CONSTRAINT fk_cjcy6i1pew56twcml3drfhef3 FOREIGN KEY (rta_office_code) REFERENCES rta_office(rta_office_id);


--
-- Name: fk_cpj1wtygg4kdpkdtxpdd3xr8a; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY attachment_details_history
    ADD CONSTRAINT fk_cpj1wtygg4kdpkdtxpdd3xr8a FOREIGN KEY (doc_types_id) REFERENCES doc_types_master(doc_type_id);


--
-- Name: fk_ct2yvxl4qga9mbyangjw10nob; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY reg_fee_details
    ADD CONSTRAINT fk_ct2yvxl4qga9mbyangjw10nob FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_cx5lpwm6ph44ki008bpbo56r8; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY finance_branch_employee
    ADD CONSTRAINT fk_cx5lpwm6ph44ki008bpbo56r8 FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: fk_d08q4uel1a946cd36tcyhxc3e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY finance_details_entity_backup
    ADD CONSTRAINT fk_d08q4uel1a946cd36tcyhxc3e FOREIGN KEY (mandal_entity) REFERENCES mandal(mandal_id);


--
-- Name: fk_d08q4uel1a946cd36tcyhxc3en; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY finance_details_entityn
    ADD CONSTRAINT fk_d08q4uel1a946cd36tcyhxc3en FOREIGN KEY (mandal_entity) REFERENCES mandal(mandal_id);


--
-- Name: fk_d18ortlxmbddo41w5ichnqd52; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY driving_institute_users
    ADD CONSTRAINT fk_d18ortlxmbddo41w5ichnqd52 FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: fk_d5ggrjrtixmtf31lnpfrwu8lp; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vehicle_noc
    ADD CONSTRAINT fk_d5ggrjrtixmtf31lnpfrwu8lp FOREIGN KEY (noc_address_id) REFERENCES vehicle_noc_address(nco_address_id);


--
-- Name: fk_dbiwgo7q5hrx49vjowvpitfx1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY finance_branch
    ADD CONSTRAINT fk_dbiwgo7q5hrx49vjowvpitfx1 FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: fk_doyhcwtgk4alcrhkpcaf45nn8; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY finance_branch
    ADD CONSTRAINT fk_doyhcwtgk4alcrhkpcaf45nn8 FOREIGN KEY (mandal_id) REFERENCES mandal(mandal_id);


--
-- Name: fk_dwx208b5kov0jrc7j18noqxrw; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vehicle_class_desc_bck
    ADD CONSTRAINT fk_dwx208b5kov0jrc7j18noqxrw FOREIGN KEY (vehicle_class_id) REFERENCES vehicle_class_bck(vehicle_class_id);


--
-- Name: fk_dwx208b5kov0jrc7j18noqxrw; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vehicle_class_desc
    ADD CONSTRAINT fk_dwx208b5kov0jrc7j18noqxrw FOREIGN KEY (vehicle_class_id) REFERENCES vehicle_class(vehicle_class_id);


--
-- Name: fk_e33eh9h0dgsasli2odomitjb0; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY license_holder_dtls
    ADD CONSTRAINT fk_e33eh9h0dgsasli2odomitjb0 FOREIGN KEY (pres_addr_state_id) REFERENCES state(state_id);


--
-- Name: fk_ediom7mpgij2x16jhnqliq0oe; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY late_fee_details_history
    ADD CONSTRAINT fk_ediom7mpgij2x16jhnqliq0oe FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_eevofs4u7ejq4xwtte855qh21; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vehicle_rc
    ADD CONSTRAINT fk_eevofs4u7ejq4xwtte855qh21 FOREIGN KEY (cco_user_id) REFERENCES users(user_id);


--
-- Name: fk_efbbjs5hmjoggqrxbxb4ovmhb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_attachment_details
    ADD CONSTRAINT fk_efbbjs5hmjoggqrxbxb4ovmhb FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: fk_ejbl7hyus60hli3sv04g8vos9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vehicle_rc
    ADD CONSTRAINT fk_ejbl7hyus60hli3sv04g8vos9 FOREIGN KEY (reg_category) REFERENCES registration_category(registration_category_id);


--
-- Name: fk_eo1qu6tbk0qtse4su6nerf65i; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY drivers_license_dtls_history
    ADD CONSTRAINT fk_eo1qu6tbk0qtse4su6nerf65i FOREIGN KEY (rta_office_code) REFERENCES rta_office(rta_office_id);


--
-- Name: fk_erfot705a2oa8w3uxdfo06ham; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vehicle_puc
    ADD CONSTRAINT fk_erfot705a2oa8w3uxdfo06ham FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_ey12ojegaov7eohw57fkghnqb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY finance_yard
    ADD CONSTRAINT fk_ey12ojegaov7eohw57fkghnqb FOREIGN KEY (state_id) REFERENCES state(state_id);


--
-- Name: fk_f05gqohhuixplg0hfk5k654pd; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY transaction_history
    ADD CONSTRAINT fk_f05gqohhuixplg0hfk5k654pd FOREIGN KEY (transaction_detail_id) REFERENCES transaction_detail(transaction_id);


--
-- Name: fk_fbiph6l65e4q6wygwe33yuptl; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY finance_branch
    ADD CONSTRAINT fk_fbiph6l65e4q6wygwe33yuptl FOREIGN KEY (district_id) REFERENCES district(district_id);


--
-- Name: fk_fec9s5vxnle3ckl7l344hyn2o; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY learners_permit_dtls
    ADD CONSTRAINT fk_fec9s5vxnle3ckl7l344hyn2o FOREIGN KEY (rta_office_code) REFERENCES rta_office(rta_office_id);


--
-- Name: fk_fgqyychn0prame86poylmnerj; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rc_card_employee
    ADD CONSTRAINT fk_fgqyychn0prame86poylmnerj FOREIGN KEY (rta_office_id) REFERENCES rta_office(rta_office_id);


--
-- Name: fk_fof215o237ovsxrp7dju3m1xy; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY hsrp_detail_history
    ADD CONSTRAINT fk_fof215o237ovsxrp7dju3m1xy FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_g1v2s8flm38gll3er3xay2fim; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vehicle_finance_details
    ADD CONSTRAINT fk_g1v2s8flm38gll3er3xay2fim FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_g2gvqhelwsj87ypyqxmpy22hf; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vehicle_rc
    ADD CONSTRAINT fk_g2gvqhelwsj87ypyqxmpy22hf FOREIGN KEY (mvi_user_id) REFERENCES users(user_id);


--
-- Name: fk_g7s4vcxjf0f6y904qpu7f9ep9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY license_holder_dtls
    ADD CONSTRAINT fk_g7s4vcxjf0f6y904qpu7f9ep9 FOREIGN KEY (licence_identity_id) REFERENCES license_identities_details(license_identity_id);


--
-- Name: fk_gsh38o1qcmjvjulrk6o6xytxc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY transaction_detail
    ADD CONSTRAINT fk_gsh38o1qcmjvjulrk6o6xytxc FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_gyjjkkm9xfy2kpdt2hf6f33ak; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vehicle_rc_history
    ADD CONSTRAINT fk_gyjjkkm9xfy2kpdt2hf6f33ak FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: fk_h0gc1coi8q4n69tn3k41k4r6d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY fitness_cert_details
    ADD CONSTRAINT fk_h0gc1coi8q4n69tn3k41k4r6d FOREIGN KEY (insp_office_code) REFERENCES rta_office(rta_office_id);


--
-- Name: fk_h54bwt2kn9kls704x5jeao13c; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY financer_fresh_rc
    ADD CONSTRAINT fk_h54bwt2kn9kls704x5jeao13c FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_h92ygm09g3ot0o5hcc75sr6w4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vehicle_rc_change_history
    ADD CONSTRAINT fk_h92ygm09g3ot0o5hcc75sr6w4 FOREIGN KEY (reg_category) REFERENCES registration_category(registration_category_id);


--
-- Name: fk_hdb3q6v2t1sf4608ulqsa4eit; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vehicle_noc_address
    ADD CONSTRAINT fk_hdb3q6v2t1sf4608ulqsa4eit FOREIGN KEY (district_id) REFERENCES district(district_id);


--
-- Name: fk_hudh7il8a5tgjl5jty7p0atdo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY learners_permit_dtls
    ADD CONSTRAINT fk_hudh7il8a5tgjl5jty7p0atdo FOREIGN KEY (mvi_user_id) REFERENCES users(user_id);


--
-- Name: fk_i5sjjtbix7kxwev1r7g4cojea; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vehicle_rc_change_history
    ADD CONSTRAINT fk_i5sjjtbix7kxwev1r7g4cojea FOREIGN KEY (mvi_user_id) REFERENCES users(user_id);


--
-- Name: fk_i96h4h1jys2bhw09f9o9c4wp4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY permit_vehicle_class_mapping
    ADD CONSTRAINT fk_i96h4h1jys2bhw09f9o9c4wp4 FOREIGN KEY (permit_type) REFERENCES permit_type_master(code);


--
-- Name: fk_iruvgsl58mcuqmcgmpbjpt0ua; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY hsrp_detail_history
    ADD CONSTRAINT fk_iruvgsl58mcuqmcgmpbjpt0ua FOREIGN KEY (hsrp_detail_id) REFERENCES hsrp_detail(hsrp_detail_id);


--
-- Name: fk_iv2nvo4i3dwybt82u4au3o1j2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY stoppage_inspection
    ADD CONSTRAINT fk_iv2nvo4i3dwybt82u4au3o1j2 FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_j3sed137rbuahdihpmb28awuj; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY financer_seized_vehicles
    ADD CONSTRAINT fk_j3sed137rbuahdihpmb28awuj FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_jx24p25briunobxvu6krorhly; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cust_corporate_details_history
    ADD CONSTRAINT fk_jx24p25briunobxvu6krorhly FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_k2fkkwyd4ltwx5kjw46pujpw; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rejection_history
    ADD CONSTRAINT fk_k2fkkwyd4ltwx5kjw46pujpw FOREIGN KEY (attachment_details_id) REFERENCES attachment_details(attachment_detail_id);


--
-- Name: fk_komd5r7sq9ly7jnkbbkh0w8nv; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY address_history
    ADD CONSTRAINT fk_komd5r7sq9ly7jnkbbkh0w8nv FOREIGN KEY (mandal_id) REFERENCES mandal(mandal_id);


--
-- Name: fk_kxfkg12u42ky8l9fmncfjoqys; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY stoppage_application
    ADD CONSTRAINT fk_kxfkg12u42ky8l9fmncfjoqys FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_kxl2o9excsv8do9na1skys5p; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY license_holder_txns
    ADD CONSTRAINT fk_kxl2o9excsv8do9na1skys5p FOREIGN KEY (rta_office_code) REFERENCES rta_office(rta_office_id);


--
-- Name: fk_ky1gyr2jwdm6giypuadc0h6k3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_attachment_details
    ADD CONSTRAINT fk_ky1gyr2jwdm6giypuadc0h6k3 FOREIGN KEY (doc_master_id) REFERENCES doc_types_master(doc_type_id);


--
-- Name: fk_leqm3nf0i43nvsby2n4sctgr9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY transaction_detail
    ADD CONSTRAINT fk_leqm3nf0i43nvsby2n4sctgr9 FOREIGN KEY (dealer_invc_id) REFERENCES dealer_invoice(dealer_invc_id);


--
-- Name: fk_lovrh4g928osmltq9ttrucmqm; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY finance_branch
    ADD CONSTRAINT fk_lovrh4g928osmltq9ttrucmqm FOREIGN KEY (state_id) REFERENCES state(state_id);


--
-- Name: fk_lxoqjm8644epv72af3k3jpalx; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY state
    ADD CONSTRAINT fk_lxoqjm8644epv72af3k3jpalx FOREIGN KEY (country_id) REFERENCES country(country_id);


--
-- Name: fk_m9e45hucucbfnma030k56ieoq; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tax_detail
    ADD CONSTRAINT fk_m9e45hucucbfnma030k56ieoq FOREIGN KEY (tax_type_id) REFERENCES tax_type(tax_type_id);


--
-- Name: fk_mao0aji8kkodtebrfs724oyx2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vehicle_details_history
    ADD CONSTRAINT fk_mao0aji8kkodtebrfs724oyx2 FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_mdv3xb1bfnxqh357egglxs6lf; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY alteration_agency_users
    ADD CONSTRAINT fk_mdv3xb1bfnxqh357egglxs6lf FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: fk_mnq1elpq0mjcigen6nth9vjm; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vehicle_rc_change_history
    ADD CONSTRAINT fk_mnq1elpq0mjcigen6nth9vjm FOREIGN KEY (rto_user_id) REFERENCES users(user_id);


--
-- Name: fk_mqn9orv42d5e0thvv0tucgv0f; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY application_slots
    ADD CONSTRAINT fk_mqn9orv42d5e0thvv0tucgv0f FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_msq59xyre2upkees5omwrcj2w; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY fitness_cert_details_history
    ADD CONSTRAINT fk_msq59xyre2upkees5omwrcj2w FOREIGN KEY (insp_office_code) REFERENCES rta_office(rta_office_id);


--
-- Name: fk_mvqwve0i2lgpl8ed49tfwlvlt; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rta_employee
    ADD CONSTRAINT fk_mvqwve0i2lgpl8ed49tfwlvlt FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: fk_new11jevcentno10bvb9ii4dp; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY fitness_cert_details
    ADD CONSTRAINT fk_new11jevcentno10bvb9ii4dp FOREIGN KEY (rta_office_code) REFERENCES rta_office(rta_office_id);


--
-- Name: fk_no4sm6o5k90ex61chhkqgmfbs; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY district
    ADD CONSTRAINT fk_no4sm6o5k90ex61chhkqgmfbs FOREIGN KEY (state_id) REFERENCES state(state_id);


--
-- Name: fk_nyperf1red7nwssfy0t5gkd3b; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tax_detail
    ADD CONSTRAINT fk_nyperf1red7nwssfy0t5gkd3b FOREIGN KEY (tax_master_id) REFERENCES tax_master(tax_master_id);


--
-- Name: fk_o0d3y9o2s7cwb28baijmvkpj6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vehicle_duplicate_registration
    ADD CONSTRAINT fk_o0d3y9o2s7cwb28baijmvkpj6 FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_o17fqlfsecfvingh6xb1vn5g; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY license_holder_txns
    ADD CONSTRAINT fk_o17fqlfsecfvingh6xb1vn5g FOREIGN KEY (pres_addr_state_id) REFERENCES state(state_id);


--
-- Name: fk_o69q20b39b8ng0ie6tm0otnd; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY finance_branch_employee
    ADD CONSTRAINT fk_o69q20b39b8ng0ie6tm0otnd FOREIGN KEY (branch_id) REFERENCES finance_branch(id);


--
-- Name: fk_ognxta8afv1335vh04p02w0yn; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY stoppage_revocation
    ADD CONSTRAINT fk_ognxta8afv1335vh04p02w0yn FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_olx0xidxrcoi2rbk6r0bfdkqy; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY attachment_details
    ADD CONSTRAINT fk_olx0xidxrcoi2rbk6r0bfdkqy FOREIGN KEY (doc_types_id) REFERENCES doc_types_master(doc_type_id);


--
-- Name: fk_p662kh6f29ac0282m8pmmgpt; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY fitness_fee_details
    ADD CONSTRAINT fk_p662kh6f29ac0282m8pmmgpt FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_pd7d6u45cq5gywl2uvujntim8; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY license_identities_details
    ADD CONSTRAINT fk_pd7d6u45cq5gywl2uvujntim8 FOREIGN KEY (rta_office_address_id) REFERENCES vehicle_noc_address(nco_address_id);


--
-- Name: fk_prvkdrmorb2rh469gjw6soeea; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY finance_details_entity_backup
    ADD CONSTRAINT fk_prvkdrmorb2rh469gjw6soeea FOREIGN KEY (state_entity) REFERENCES state(state_id);


--
-- Name: fk_prvkdrmorb2rh469gjw6soeean; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY finance_details_entityn
    ADD CONSTRAINT fk_prvkdrmorb2rh469gjw6soeean FOREIGN KEY (state_entity) REFERENCES state(state_id);


--
-- Name: fk_q32bbjc1rrt1qmqj5ahk5fc6v; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY life_tax_details
    ADD CONSTRAINT fk_q32bbjc1rrt1qmqj5ahk5fc6v FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_q6p476dmchysm8hdn46fsssyk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY hsrp_fee_details
    ADD CONSTRAINT fk_q6p476dmchysm8hdn46fsssyk FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_qrgb17lkxk4qxgtv2wlcum18s; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY finance_app_status
    ADD CONSTRAINT fk_qrgb17lkxk4qxgtv2wlcum18s FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: fk_qxav8fjl2r69v0bg7q79p5lsh; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY drivers_license_dtls
    ADD CONSTRAINT fk_qxav8fjl2r69v0bg7q79p5lsh FOREIGN KEY (ao_user_id) REFERENCES users(user_id);


--
-- Name: fk_r9dwheh83k24lrelyc8ptg5bi; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cess_fee_details
    ADD CONSTRAINT fk_r9dwheh83k24lrelyc8ptg5bi FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_rbq5j97o6390s6kkl9pyw859g; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY license_holder_txns
    ADD CONSTRAINT fk_rbq5j97o6390s6kkl9pyw859g FOREIGN KEY (qualification_id) REFERENCES qualification(qualification_id);


--
-- Name: fk_rk7kfimggdk8luof98v1pru2h; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rta_mandal_mapping
    ADD CONSTRAINT fk_rk7kfimggdk8luof98v1pru2h FOREIGN KEY (rta_office_id) REFERENCES rta_office(rta_office_id);


--
-- Name: fk_rll23felr0r3yvdhub4q2tt0o; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY fitness_cert_details_history
    ADD CONSTRAINT fk_rll23felr0r3yvdhub4q2tt0o FOREIGN KEY (rta_office_code) REFERENCES rta_office(rta_office_id);


--
-- Name: fk_rmah5wmtvd6n3c01l5yy87qi4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY license_holder_dtls
    ADD CONSTRAINT fk_rmah5wmtvd6n3c01l5yy87qi4 FOREIGN KEY (qualification_id) REFERENCES qualification(qualification_id);


--
-- Name: fk_rogo4fpoxgj8uq4oq4hh21v1l; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY driving_institute_users_info
    ADD CONSTRAINT fk_rogo4fpoxgj8uq4oq4hh21v1l FOREIGN KEY (driving_institute_users_id) REFERENCES driving_institute_users(driving_institute_users_id);


--
-- Name: fk_rpu0a1s4ghn2ldoiq4mx08yiu; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vehicle_rc_change_history
    ADD CONSTRAINT fk_rpu0a1s4ghn2ldoiq4mx08yiu FOREIGN KEY (cco_user_id) REFERENCES users(user_id);


--
-- Name: fk_rvo1q1fnkdpeihd7v5g5ums8r; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY hsrp_detail
    ADD CONSTRAINT fk_rvo1q1fnkdpeihd7v5g5ums8r FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_rvpkyh89umbbneoxm1ccxmgk5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cess_fee_details_history
    ADD CONSTRAINT fk_rvpkyh89umbbneoxm1ccxmgk5 FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_ryic2mvnae6t63voc9n9lmmg3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY finance_yard
    ADD CONSTRAINT fk_ryic2mvnae6t63voc9n9lmmg3 FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: fk_sebk62bb9qm03h8hjl4f0wamd; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rejection_history
    ADD CONSTRAINT fk_sebk62bb9qm03h8hjl4f0wamd FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_sjyvnw94b4i3egxgha2kg907h; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY medical_practitioner_users
    ADD CONSTRAINT fk_sjyvnw94b4i3egxgha2kg907h FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: fk_sm1ag45m811diklglfym47sme; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY drivers_license_dtls
    ADD CONSTRAINT fk_sm1ag45m811diklglfym47sme FOREIGN KEY (mvi_user_id) REFERENCES users(user_id);


--
-- Name: fk_t4mxfivhdmayrvr261f1f19n0; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vehicle_details
    ADD CONSTRAINT fk_t4mxfivhdmayrvr261f1f19n0 FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_t5bfrvpeso9bqxcg0l4nx1sal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vehicle_rc
    ADD CONSTRAINT fk_t5bfrvpeso9bqxcg0l4nx1sal FOREIGN KEY (rta_office_id) REFERENCES rta_office(rta_office_id);


--
-- Name: fk_t6p5mg9bbh2iloeeash792f50; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY finance_yard
    ADD CONSTRAINT fk_t6p5mg9bbh2iloeeash792f50 FOREIGN KEY (mandal_id) REFERENCES mandal(mandal_id);


--
-- Name: fk_tavu6jcu33gc33xn27esehl6l; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY license_holder_dtls
    ADD CONSTRAINT fk_tavu6jcu33gc33xn27esehl6l FOREIGN KEY (rta_office_code) REFERENCES rta_office(rta_office_id);


--
-- Name: fk_tf4dosyj6l9g5va6pdmrt6ls9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY hsrp_detail
    ADD CONSTRAINT fk_tf4dosyj6l9g5va6pdmrt6ls9 FOREIGN KEY (hsrp_transaction_id) REFERENCES hsrp_transaction(hsrp_transaction_id);


--
-- Name: fk_tfe1fnug3rokm90ou9oeywp4c; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY hsrp_transaction
    ADD CONSTRAINT fk_tfe1fnug3rokm90ou9oeywp4c FOREIGN KEY (vehicle_rc_id) REFERENCES vehicle_rc(vehicle_rc_id);


--
-- Name: fk_tltiuhs00at6fruq35wweak3b; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rejection_history
    ADD CONSTRAINT fk_tltiuhs00at6fruq35wweak3b FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: fk_tq481ownhnymk16w1io2mqghh; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY dealer
    ADD CONSTRAINT fk_tq481ownhnymk16w1io2mqghh FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: fk_tqams33qhkmefb9t9iayvdj0b; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY license_holder_dtls
    ADD CONSTRAINT fk_tqams33qhkmefb9t9iayvdj0b FOREIGN KEY (pres_addr_district_id) REFERENCES district(district_id);


--
-- Name: fk_u4sycqe7ytr01hem6k66t0oi; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY body_builder_users
    ADD CONSTRAINT fk_u4sycqe7ytr01hem6k66t0oi FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: public; Type: ACL; Schema: -; Owner: rtamigprod
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM rtamigprod;
GRANT ALL ON SCHEMA public TO rtamigprod;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;
GRANT USAGE ON SCHEMA public TO prod_read_user;


--
-- Name: aadhaar_log_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE aadhaar_log_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE aadhaar_log_seq FROM postgres;
GRANT ALL ON SEQUENCE aadhaar_log_seq TO postgres;
GRANT ALL ON SEQUENCE aadhaar_log_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE aadhaar_log_seq TO prod_read_user;


--
-- Name: aadhaar_logs; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE aadhaar_logs FROM PUBLIC;
REVOKE ALL ON TABLE aadhaar_logs FROM postgres;
GRANT ALL ON TABLE aadhaar_logs TO postgres;
GRANT ALL ON TABLE aadhaar_logs TO rtamigprod;
GRANT SELECT ON TABLE aadhaar_logs TO prod_read_user;


--
-- Name: aadhaar_master; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE aadhaar_master FROM PUBLIC;
REVOKE ALL ON TABLE aadhaar_master FROM postgres;
GRANT ALL ON TABLE aadhaar_master TO postgres;
GRANT ALL ON TABLE aadhaar_master TO rtamigprod;
GRANT SELECT ON TABLE aadhaar_master TO prod_read_user;


--
-- Name: aadhar_master_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE aadhar_master_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE aadhar_master_seq FROM postgres;
GRANT ALL ON SEQUENCE aadhar_master_seq TO postgres;
GRANT ALL ON SEQUENCE aadhar_master_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE aadhar_master_seq TO prod_read_user;


--
-- Name: address; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE address FROM PUBLIC;
REVOKE ALL ON TABLE address FROM postgres;
GRANT ALL ON TABLE address TO postgres;
GRANT ALL ON TABLE address TO rtamigprod;
GRANT SELECT ON TABLE address TO prod_read_user;


--
-- Name: address_history; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE address_history FROM PUBLIC;
REVOKE ALL ON TABLE address_history FROM postgres;
GRANT ALL ON TABLE address_history TO postgres;
GRANT ALL ON TABLE address_history TO rtamigprod;
GRANT SELECT ON TABLE address_history TO prod_read_user;


--
-- Name: address_history_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE address_history_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE address_history_seq FROM postgres;
GRANT ALL ON SEQUENCE address_history_seq TO postgres;
GRANT ALL ON SEQUENCE address_history_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE address_history_seq TO prod_read_user;


--
-- Name: address_proof_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE address_proof_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE address_proof_seq FROM postgres;
GRANT ALL ON SEQUENCE address_proof_seq TO postgres;
GRANT ALL ON SEQUENCE address_proof_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE address_proof_seq TO prod_read_user;


--
-- Name: address_proof_type; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE address_proof_type FROM PUBLIC;
REVOKE ALL ON TABLE address_proof_type FROM postgres;
GRANT ALL ON TABLE address_proof_type TO postgres;
GRANT ALL ON TABLE address_proof_type TO rtamigprod;
GRANT SELECT ON TABLE address_proof_type TO prod_read_user;


--
-- Name: address_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE address_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE address_seq FROM postgres;
GRANT ALL ON SEQUENCE address_seq TO postgres;
GRANT ALL ON SEQUENCE address_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE address_seq TO prod_read_user;


--
-- Name: affixation_center_master; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE affixation_center_master FROM PUBLIC;
REVOKE ALL ON TABLE affixation_center_master FROM postgres;
GRANT ALL ON TABLE affixation_center_master TO postgres;
GRANT ALL ON TABLE affixation_center_master TO rtamigprod;
GRANT SELECT ON TABLE affixation_center_master TO prod_read_user;


--
-- Name: affixation_center_master_sequence; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE affixation_center_master_sequence FROM PUBLIC;
REVOKE ALL ON SEQUENCE affixation_center_master_sequence FROM postgres;
GRANT ALL ON SEQUENCE affixation_center_master_sequence TO postgres;
GRANT ALL ON SEQUENCE affixation_center_master_sequence TO rtamigprod;
GRANT SELECT ON SEQUENCE affixation_center_master_sequence TO prod_read_user;


--
-- Name: age_group_ref; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE age_group_ref FROM PUBLIC;
REVOKE ALL ON TABLE age_group_ref FROM postgres;
GRANT ALL ON TABLE age_group_ref TO postgres;
GRANT ALL ON TABLE age_group_ref TO rtamigprod;
GRANT SELECT ON TABLE age_group_ref TO prod_read_user;


--
-- Name: alteration_agency_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE alteration_agency_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE alteration_agency_seq FROM postgres;
GRANT ALL ON SEQUENCE alteration_agency_seq TO postgres;
GRANT ALL ON SEQUENCE alteration_agency_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE alteration_agency_seq TO prod_read_user;


--
-- Name: alteration_agency_users; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE alteration_agency_users FROM PUBLIC;
REVOKE ALL ON TABLE alteration_agency_users FROM postgres;
GRANT ALL ON TABLE alteration_agency_users TO postgres;
GRANT ALL ON TABLE alteration_agency_users TO rtamigprod;
GRANT SELECT ON TABLE alteration_agency_users TO prod_read_user;


--
-- Name: alteration_cov_mapping; Type: ACL; Schema: public; Owner: rtamigprod
--

REVOKE ALL ON TABLE alteration_cov_mapping FROM PUBLIC;
REVOKE ALL ON TABLE alteration_cov_mapping FROM rtamigprod;
GRANT ALL ON TABLE alteration_cov_mapping TO rtamigprod;
GRANT SELECT ON TABLE alteration_cov_mapping TO prod_read_user;


--
-- Name: alteration_cov_mapping_id_seq; Type: ACL; Schema: public; Owner: rtamigprod
--

REVOKE ALL ON SEQUENCE alteration_cov_mapping_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE alteration_cov_mapping_id_seq FROM rtamigprod;
GRANT ALL ON SEQUENCE alteration_cov_mapping_id_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE alteration_cov_mapping_id_seq TO prod_read_user;


--
-- Name: app_version; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE app_version FROM PUBLIC;
REVOKE ALL ON TABLE app_version FROM postgres;
GRANT ALL ON TABLE app_version TO postgres;
GRANT ALL ON TABLE app_version TO rtamigprod;
GRANT SELECT ON TABLE app_version TO prod_read_user;


--
-- Name: app_version_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE app_version_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE app_version_seq FROM postgres;
GRANT ALL ON SEQUENCE app_version_seq TO postgres;
GRANT ALL ON SEQUENCE app_version_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE app_version_seq TO prod_read_user;


--
-- Name: application_slot_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE application_slot_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE application_slot_seq FROM postgres;
GRANT ALL ON SEQUENCE application_slot_seq TO postgres;
GRANT ALL ON SEQUENCE application_slot_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE application_slot_seq TO prod_read_user;


--
-- Name: application_slots; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE application_slots FROM PUBLIC;
REVOKE ALL ON TABLE application_slots FROM postgres;
GRANT ALL ON TABLE application_slots TO postgres;
GRANT ALL ON TABLE application_slots TO rtamigprod;
GRANT SELECT ON TABLE application_slots TO prod_read_user;


--
-- Name: attachment_details; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE attachment_details FROM PUBLIC;
REVOKE ALL ON TABLE attachment_details FROM postgres;
GRANT ALL ON TABLE attachment_details TO postgres;
GRANT ALL ON TABLE attachment_details TO rtamigprod;
GRANT SELECT ON TABLE attachment_details TO prod_read_user;


--
-- Name: attachment_details_history; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE attachment_details_history FROM PUBLIC;
REVOKE ALL ON TABLE attachment_details_history FROM postgres;
GRANT ALL ON TABLE attachment_details_history TO postgres;
GRANT ALL ON TABLE attachment_details_history TO rtamigprod;
GRANT SELECT ON TABLE attachment_details_history TO prod_read_user;


--
-- Name: attachment_details_history_sequence; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE attachment_details_history_sequence FROM PUBLIC;
REVOKE ALL ON SEQUENCE attachment_details_history_sequence FROM postgres;
GRANT ALL ON SEQUENCE attachment_details_history_sequence TO postgres;
GRANT ALL ON SEQUENCE attachment_details_history_sequence TO rtamigprod;
GRANT SELECT ON SEQUENCE attachment_details_history_sequence TO prod_read_user;


--
-- Name: attachment_details_sequence; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE attachment_details_sequence FROM PUBLIC;
REVOKE ALL ON SEQUENCE attachment_details_sequence FROM postgres;
GRANT ALL ON SEQUENCE attachment_details_sequence TO postgres;
GRANT ALL ON SEQUENCE attachment_details_sequence TO rtamigprod;
GRANT SELECT ON SEQUENCE attachment_details_sequence TO prod_read_user;


--
-- Name: bharat_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE bharat_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE bharat_seq FROM postgres;
GRANT ALL ON SEQUENCE bharat_seq TO postgres;
GRANT ALL ON SEQUENCE bharat_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE bharat_seq TO prod_read_user;


--
-- Name: body_builder_users; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE body_builder_users FROM PUBLIC;
REVOKE ALL ON TABLE body_builder_users FROM postgres;
GRANT ALL ON TABLE body_builder_users TO postgres;
GRANT ALL ON TABLE body_builder_users TO rtamigprod;
GRANT SELECT ON TABLE body_builder_users TO prod_read_user;


--
-- Name: body_builder_users_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE body_builder_users_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE body_builder_users_seq FROM postgres;
GRANT ALL ON SEQUENCE body_builder_users_seq TO postgres;
GRANT ALL ON SEQUENCE body_builder_users_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE body_builder_users_seq TO prod_read_user;


--
-- Name: body_type_master; Type: ACL; Schema: public; Owner: rtamigprod
--

REVOKE ALL ON TABLE body_type_master FROM PUBLIC;
REVOKE ALL ON TABLE body_type_master FROM rtamigprod;
GRANT ALL ON TABLE body_type_master TO rtamigprod;
GRANT SELECT ON TABLE body_type_master TO prod_read_user;


--
-- Name: body_type_master_id_seq; Type: ACL; Schema: public; Owner: rtamigprod
--

REVOKE ALL ON SEQUENCE body_type_master_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE body_type_master_id_seq FROM rtamigprod;
GRANT ALL ON SEQUENCE body_type_master_id_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE body_type_master_id_seq TO prod_read_user;


--
-- Name: cess_fee_details; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE cess_fee_details FROM PUBLIC;
REVOKE ALL ON TABLE cess_fee_details FROM postgres;
GRANT ALL ON TABLE cess_fee_details TO postgres;
GRANT ALL ON TABLE cess_fee_details TO rtamigprod;
GRANT SELECT ON TABLE cess_fee_details TO prod_read_user;


--
-- Name: cess_fee_details_history; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE cess_fee_details_history FROM PUBLIC;
REVOKE ALL ON TABLE cess_fee_details_history FROM postgres;
GRANT ALL ON TABLE cess_fee_details_history TO postgres;
GRANT ALL ON TABLE cess_fee_details_history TO rtamigprod;
GRANT SELECT ON TABLE cess_fee_details_history TO prod_read_user;


--
-- Name: cess_fee_dtl_hist_seq; Type: ACL; Schema: public; Owner: rtamigprod
--

REVOKE ALL ON SEQUENCE cess_fee_dtl_hist_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE cess_fee_dtl_hist_seq FROM rtamigprod;
GRANT ALL ON SEQUENCE cess_fee_dtl_hist_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE cess_fee_dtl_hist_seq TO prod_read_user;


--
-- Name: cess_fee_dtl_seq; Type: ACL; Schema: public; Owner: rtamigprod
--

REVOKE ALL ON SEQUENCE cess_fee_dtl_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE cess_fee_dtl_seq FROM rtamigprod;
GRANT ALL ON SEQUENCE cess_fee_dtl_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE cess_fee_dtl_seq TO prod_read_user;


--
-- Name: cfx_note_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE cfx_note_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE cfx_note_seq FROM postgres;
GRANT ALL ON SEQUENCE cfx_note_seq TO postgres;
GRANT ALL ON SEQUENCE cfx_note_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE cfx_note_seq TO prod_read_user;


--
-- Name: cfx_notes; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE cfx_notes FROM PUBLIC;
REVOKE ALL ON TABLE cfx_notes FROM postgres;
GRANT ALL ON TABLE cfx_notes TO postgres;
GRANT ALL ON TABLE cfx_notes TO rtamigprod;
GRANT SELECT ON TABLE cfx_notes TO prod_read_user;


--
-- Name: cfx_txn_dtl; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE cfx_txn_dtl FROM PUBLIC;
REVOKE ALL ON TABLE cfx_txn_dtl FROM postgres;
GRANT ALL ON TABLE cfx_txn_dtl TO postgres;
GRANT ALL ON TABLE cfx_txn_dtl TO rtamigprod;
GRANT SELECT ON TABLE cfx_txn_dtl TO prod_read_user;


--
-- Name: cfx_txn_dtl_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE cfx_txn_dtl_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE cfx_txn_dtl_seq FROM postgres;
GRANT ALL ON SEQUENCE cfx_txn_dtl_seq TO postgres;
GRANT ALL ON SEQUENCE cfx_txn_dtl_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE cfx_txn_dtl_seq TO prod_read_user;


--
-- Name: challan_number; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE challan_number FROM PUBLIC;
REVOKE ALL ON TABLE challan_number FROM postgres;
GRANT ALL ON TABLE challan_number TO postgres;
GRANT ALL ON TABLE challan_number TO rtamigprod;
GRANT SELECT ON TABLE challan_number TO prod_read_user;


--
-- Name: challan_number_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE challan_number_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE challan_number_seq FROM postgres;
GRANT ALL ON SEQUENCE challan_number_seq TO postgres;
GRANT ALL ON SEQUENCE challan_number_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE challan_number_seq TO prod_read_user;


--
-- Name: cms_sync_vehicle; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE cms_sync_vehicle FROM PUBLIC;
REVOKE ALL ON TABLE cms_sync_vehicle FROM postgres;
GRANT ALL ON TABLE cms_sync_vehicle TO postgres;
GRANT ALL ON TABLE cms_sync_vehicle TO rtamigprod;
GRANT SELECT ON TABLE cms_sync_vehicle TO prod_read_user;


--
-- Name: cms_sync_vehicle_sequence; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE cms_sync_vehicle_sequence FROM PUBLIC;
REVOKE ALL ON SEQUENCE cms_sync_vehicle_sequence FROM postgres;
GRANT ALL ON SEQUENCE cms_sync_vehicle_sequence TO postgres;
GRANT ALL ON SEQUENCE cms_sync_vehicle_sequence TO rtamigprod;
GRANT SELECT ON SEQUENCE cms_sync_vehicle_sequence TO prod_read_user;


--
-- Name: country; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE country FROM PUBLIC;
REVOKE ALL ON TABLE country FROM postgres;
GRANT ALL ON TABLE country TO postgres;
GRANT ALL ON TABLE country TO rtamigprod;
GRANT SELECT ON TABLE country TO prod_read_user;


--
-- Name: country_id_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE country_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE country_id_seq FROM postgres;
GRANT ALL ON SEQUENCE country_id_seq TO postgres;
GRANT ALL ON SEQUENCE country_id_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE country_id_seq TO prod_read_user;


--
-- Name: currenttaxjoinperiodic; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE currenttaxjoinperiodic FROM PUBLIC;
REVOKE ALL ON TABLE currenttaxjoinperiodic FROM postgres;
GRANT ALL ON TABLE currenttaxjoinperiodic TO postgres;
GRANT ALL ON TABLE currenttaxjoinperiodic TO rtamigprod;
GRANT SELECT ON TABLE currenttaxjoinperiodic TO prod_read_user;


--
-- Name: currenttaxupddata; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE currenttaxupddata FROM PUBLIC;
REVOKE ALL ON TABLE currenttaxupddata FROM postgres;
GRANT ALL ON TABLE currenttaxupddata TO postgres;
GRANT ALL ON TABLE currenttaxupddata TO rtamigprod;
GRANT SELECT ON TABLE currenttaxupddata TO prod_read_user;


--
-- Name: cust_corporate_details; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE cust_corporate_details FROM PUBLIC;
REVOKE ALL ON TABLE cust_corporate_details FROM postgres;
GRANT ALL ON TABLE cust_corporate_details TO postgres;
GRANT ALL ON TABLE cust_corporate_details TO rtamigprod;
GRANT SELECT ON TABLE cust_corporate_details TO prod_read_user;


--
-- Name: cust_corporate_details_history; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE cust_corporate_details_history FROM PUBLIC;
REVOKE ALL ON TABLE cust_corporate_details_history FROM postgres;
GRANT ALL ON TABLE cust_corporate_details_history TO postgres;
GRANT ALL ON TABLE cust_corporate_details_history TO rtamigprod;
GRANT SELECT ON TABLE cust_corporate_details_history TO prod_read_user;


--
-- Name: cust_individual_details; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE cust_individual_details FROM PUBLIC;
REVOKE ALL ON TABLE cust_individual_details FROM postgres;
GRANT ALL ON TABLE cust_individual_details TO postgres;
GRANT ALL ON TABLE cust_individual_details TO rtamigprod;
GRANT SELECT ON TABLE cust_individual_details TO prod_read_user;


--
-- Name: cust_individual_details_history; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE cust_individual_details_history FROM PUBLIC;
REVOKE ALL ON TABLE cust_individual_details_history FROM postgres;
GRANT ALL ON TABLE cust_individual_details_history TO postgres;
GRANT ALL ON TABLE cust_individual_details_history TO rtamigprod;
GRANT SELECT ON TABLE cust_individual_details_history TO prod_read_user;


--
-- Name: dealer; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE dealer FROM PUBLIC;
REVOKE ALL ON TABLE dealer FROM postgres;
GRANT ALL ON TABLE dealer TO postgres;
GRANT ALL ON TABLE dealer TO rtamigprod;
GRANT SELECT ON TABLE dealer TO prod_read_user;


--
-- Name: dealer_inv_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE dealer_inv_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE dealer_inv_seq FROM postgres;
GRANT ALL ON SEQUENCE dealer_inv_seq TO postgres;
GRANT ALL ON SEQUENCE dealer_inv_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE dealer_inv_seq TO prod_read_user;


--
-- Name: dealer_invoice; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE dealer_invoice FROM PUBLIC;
REVOKE ALL ON TABLE dealer_invoice FROM postgres;
GRANT ALL ON TABLE dealer_invoice TO postgres;
GRANT ALL ON TABLE dealer_invoice TO rtamigprod;
GRANT SELECT ON TABLE dealer_invoice TO prod_read_user;


--
-- Name: dealer_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE dealer_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE dealer_seq FROM postgres;
GRANT ALL ON SEQUENCE dealer_seq TO postgres;
GRANT ALL ON SEQUENCE dealer_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE dealer_seq TO prod_read_user;


--
-- Name: designation; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE designation FROM PUBLIC;
REVOKE ALL ON TABLE designation FROM postgres;
GRANT ALL ON TABLE designation TO postgres;
GRANT ALL ON TABLE designation TO rtamigprod;
GRANT SELECT ON TABLE designation TO prod_read_user;


--
-- Name: designation_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE designation_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE designation_seq FROM postgres;
GRANT ALL ON SEQUENCE designation_seq TO postgres;
GRANT ALL ON SEQUENCE designation_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE designation_seq TO prod_read_user;


--
-- Name: district; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE district FROM PUBLIC;
REVOKE ALL ON TABLE district FROM postgres;
GRANT ALL ON TABLE district TO postgres;
GRANT ALL ON TABLE district TO rtamigprod;
GRANT SELECT ON TABLE district TO prod_read_user;


--
-- Name: district_id_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE district_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE district_id_seq FROM postgres;
GRANT ALL ON SEQUENCE district_id_seq TO postgres;
GRANT ALL ON SEQUENCE district_id_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE district_id_seq TO prod_read_user;


--
-- Name: doc_permission; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE doc_permission FROM PUBLIC;
REVOKE ALL ON TABLE doc_permission FROM postgres;
GRANT ALL ON TABLE doc_permission TO postgres;
GRANT ALL ON TABLE doc_permission TO rtamigprod;
GRANT SELECT ON TABLE doc_permission TO prod_read_user;


--
-- Name: doc_permission_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE doc_permission_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE doc_permission_seq FROM postgres;
GRANT ALL ON SEQUENCE doc_permission_seq TO postgres;
GRANT ALL ON SEQUENCE doc_permission_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE doc_permission_seq TO prod_read_user;


--
-- Name: doc_types_master; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE doc_types_master FROM PUBLIC;
REVOKE ALL ON TABLE doc_types_master FROM postgres;
GRANT ALL ON TABLE doc_types_master TO postgres;
GRANT ALL ON TABLE doc_types_master TO rtamigprod;
GRANT SELECT ON TABLE doc_types_master TO prod_read_user;


--
-- Name: doc_types_sequence; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE doc_types_sequence FROM PUBLIC;
REVOKE ALL ON SEQUENCE doc_types_sequence FROM postgres;
GRANT ALL ON SEQUENCE doc_types_sequence TO postgres;
GRANT ALL ON SEQUENCE doc_types_sequence TO rtamigprod;
GRANT SELECT ON SEQUENCE doc_types_sequence TO prod_read_user;


--
-- Name: drivers_license_dtls; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE drivers_license_dtls FROM PUBLIC;
REVOKE ALL ON TABLE drivers_license_dtls FROM postgres;
GRANT ALL ON TABLE drivers_license_dtls TO postgres;
GRANT ALL ON TABLE drivers_license_dtls TO rtamigprod;
GRANT SELECT ON TABLE drivers_license_dtls TO prod_read_user;


--
-- Name: drivers_license_dtls_history; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE drivers_license_dtls_history FROM PUBLIC;
REVOKE ALL ON TABLE drivers_license_dtls_history FROM postgres;
GRANT ALL ON TABLE drivers_license_dtls_history TO postgres;
GRANT ALL ON TABLE drivers_license_dtls_history TO rtamigprod;
GRANT SELECT ON TABLE drivers_license_dtls_history TO prod_read_user;


--
-- Name: drivers_license_hist_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE drivers_license_hist_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE drivers_license_hist_seq FROM postgres;
GRANT ALL ON SEQUENCE drivers_license_hist_seq TO postgres;
GRANT ALL ON SEQUENCE drivers_license_hist_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE drivers_license_hist_seq TO prod_read_user;


--
-- Name: driving_institute_users; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE driving_institute_users FROM PUBLIC;
REVOKE ALL ON TABLE driving_institute_users FROM postgres;
GRANT ALL ON TABLE driving_institute_users TO postgres;
GRANT ALL ON TABLE driving_institute_users TO rtamigprod;
GRANT SELECT ON TABLE driving_institute_users TO prod_read_user;


--
-- Name: driving_institute_users_info; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE driving_institute_users_info FROM PUBLIC;
REVOKE ALL ON TABLE driving_institute_users_info FROM postgres;
GRANT ALL ON TABLE driving_institute_users_info TO postgres;
GRANT ALL ON TABLE driving_institute_users_info TO rtamigprod;
GRANT SELECT ON TABLE driving_institute_users_info TO prod_read_user;


--
-- Name: driving_institute_users_info_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE driving_institute_users_info_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE driving_institute_users_info_seq FROM postgres;
GRANT ALL ON SEQUENCE driving_institute_users_info_seq TO postgres;
GRANT ALL ON SEQUENCE driving_institute_users_info_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE driving_institute_users_info_seq TO prod_read_user;


--
-- Name: driving_institute_users_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE driving_institute_users_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE driving_institute_users_seq FROM postgres;
GRANT ALL ON SEQUENCE driving_institute_users_seq TO postgres;
GRANT ALL ON SEQUENCE driving_institute_users_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE driving_institute_users_seq TO prod_read_user;


--
-- Name: event_id_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE event_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE event_id_seq FROM postgres;
GRANT ALL ON SEQUENCE event_id_seq TO postgres;
GRANT ALL ON SEQUENCE event_id_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE event_id_seq TO prod_read_user;


--
-- Name: event_log; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE event_log FROM PUBLIC;
REVOKE ALL ON TABLE event_log FROM postgres;
GRANT ALL ON TABLE event_log TO postgres;
GRANT ALL ON TABLE event_log TO rtamigprod;
GRANT SELECT ON TABLE event_log TO prod_read_user;


--
-- Name: finance_app_status; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE finance_app_status FROM PUBLIC;
REVOKE ALL ON TABLE finance_app_status FROM postgres;
GRANT ALL ON TABLE finance_app_status TO postgres;
GRANT ALL ON TABLE finance_app_status TO rtamigprod;
GRANT SELECT ON TABLE finance_app_status TO prod_read_user;


--
-- Name: finance_approval_history_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE finance_approval_history_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE finance_approval_history_seq FROM postgres;
GRANT ALL ON SEQUENCE finance_approval_history_seq TO postgres;
GRANT ALL ON SEQUENCE finance_approval_history_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE finance_approval_history_seq TO prod_read_user;


--
-- Name: finance_approve_details; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE finance_approve_details FROM PUBLIC;
REVOKE ALL ON TABLE finance_approve_details FROM postgres;
GRANT ALL ON TABLE finance_approve_details TO postgres;
GRANT ALL ON TABLE finance_approve_details TO rtamigprod;
GRANT SELECT ON TABLE finance_approve_details TO prod_read_user;


--
-- Name: finance_approve_details_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE finance_approve_details_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE finance_approve_details_seq FROM postgres;
GRANT ALL ON SEQUENCE finance_approve_details_seq TO postgres;
GRANT ALL ON SEQUENCE finance_approve_details_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE finance_approve_details_seq TO prod_read_user;


--
-- Name: finance_approve_history; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE finance_approve_history FROM PUBLIC;
REVOKE ALL ON TABLE finance_approve_history FROM postgres;
GRANT ALL ON TABLE finance_approve_history TO postgres;
GRANT ALL ON TABLE finance_approve_history TO rtamigprod;
GRANT SELECT ON TABLE finance_approve_history TO prod_read_user;


--
-- Name: finance_branch; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE finance_branch FROM PUBLIC;
REVOKE ALL ON TABLE finance_branch FROM postgres;
GRANT ALL ON TABLE finance_branch TO postgres;
GRANT ALL ON TABLE finance_branch TO rtamigprod;
GRANT SELECT ON TABLE finance_branch TO prod_read_user;


--
-- Name: finance_branch_employee; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE finance_branch_employee FROM PUBLIC;
REVOKE ALL ON TABLE finance_branch_employee FROM postgres;
GRANT ALL ON TABLE finance_branch_employee TO postgres;
GRANT ALL ON TABLE finance_branch_employee TO rtamigprod;
GRANT SELECT ON TABLE finance_branch_employee TO prod_read_user;


--
-- Name: finance_branch_employee_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE finance_branch_employee_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE finance_branch_employee_seq FROM postgres;
GRANT ALL ON SEQUENCE finance_branch_employee_seq TO postgres;
GRANT ALL ON SEQUENCE finance_branch_employee_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE finance_branch_employee_seq TO prod_read_user;


--
-- Name: finance_branch_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE finance_branch_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE finance_branch_seq FROM postgres;
GRANT ALL ON SEQUENCE finance_branch_seq TO postgres;
GRANT ALL ON SEQUENCE finance_branch_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE finance_branch_seq TO prod_read_user;


--
-- Name: finance_details_entity; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE finance_details_entity FROM PUBLIC;
REVOKE ALL ON TABLE finance_details_entity FROM postgres;
GRANT ALL ON TABLE finance_details_entity TO postgres;
GRANT ALL ON TABLE finance_details_entity TO rtamigprod;
GRANT SELECT ON TABLE finance_details_entity TO prod_read_user;


--
-- Name: finance_details_entity_backup; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE finance_details_entity_backup FROM PUBLIC;
REVOKE ALL ON TABLE finance_details_entity_backup FROM postgres;
GRANT ALL ON TABLE finance_details_entity_backup TO postgres;
GRANT ALL ON TABLE finance_details_entity_backup TO rtamigprod;
GRANT SELECT ON TABLE finance_details_entity_backup TO prod_read_user;


--
-- Name: finance_details_entityn; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE finance_details_entityn FROM PUBLIC;
REVOKE ALL ON TABLE finance_details_entityn FROM postgres;
GRANT ALL ON TABLE finance_details_entityn TO postgres;
GRANT ALL ON TABLE finance_details_entityn TO rtamigprod;
GRANT SELECT ON TABLE finance_details_entityn TO prod_read_user;


--
-- Name: finance_history; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE finance_history FROM PUBLIC;
REVOKE ALL ON TABLE finance_history FROM postgres;
GRANT ALL ON TABLE finance_history TO postgres;
GRANT ALL ON TABLE finance_history TO rtamigprod;
GRANT SELECT ON TABLE finance_history TO prod_read_user;


--
-- Name: finance_token; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE finance_token FROM PUBLIC;
REVOKE ALL ON TABLE finance_token FROM postgres;
GRANT ALL ON TABLE finance_token TO postgres;
GRANT ALL ON TABLE finance_token TO rtamigprod;
GRANT SELECT ON TABLE finance_token TO prod_read_user;


--
-- Name: finance_token_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE finance_token_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE finance_token_seq FROM postgres;
GRANT ALL ON SEQUENCE finance_token_seq TO postgres;
GRANT ALL ON SEQUENCE finance_token_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE finance_token_seq TO prod_read_user;


--
-- Name: finance_yard; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE finance_yard FROM PUBLIC;
REVOKE ALL ON TABLE finance_yard FROM postgres;
GRANT ALL ON TABLE finance_yard TO postgres;
GRANT ALL ON TABLE finance_yard TO rtamigprod;
GRANT SELECT ON TABLE finance_yard TO prod_read_user;


--
-- Name: finance_yard_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE finance_yard_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE finance_yard_seq FROM postgres;
GRANT ALL ON SEQUENCE finance_yard_seq TO postgres;
GRANT ALL ON SEQUENCE finance_yard_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE finance_yard_seq TO prod_read_user;


--
-- Name: financehistory_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE financehistory_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE financehistory_seq FROM postgres;
GRANT ALL ON SEQUENCE financehistory_seq TO postgres;
GRANT ALL ON SEQUENCE financehistory_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE financehistory_seq TO prod_read_user;


--
-- Name: financer_app_status_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE financer_app_status_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE financer_app_status_seq FROM postgres;
GRANT ALL ON SEQUENCE financer_app_status_seq TO postgres;
GRANT ALL ON SEQUENCE financer_app_status_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE financer_app_status_seq TO prod_read_user;


--
-- Name: financer_fresh_contact_details; Type: ACL; Schema: public; Owner: rtamigprod
--

REVOKE ALL ON TABLE financer_fresh_contact_details FROM PUBLIC;
REVOKE ALL ON TABLE financer_fresh_contact_details FROM rtamigprod;
GRANT ALL ON TABLE financer_fresh_contact_details TO rtamigprod;
GRANT SELECT ON TABLE financer_fresh_contact_details TO prod_read_user;


--
-- Name: financer_fresh_contact_details_seq; Type: ACL; Schema: public; Owner: rtamigprod
--

REVOKE ALL ON SEQUENCE financer_fresh_contact_details_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE financer_fresh_contact_details_seq FROM rtamigprod;
GRANT ALL ON SEQUENCE financer_fresh_contact_details_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE financer_fresh_contact_details_seq TO prod_read_user;


--
-- Name: financer_fresh_rc; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE financer_fresh_rc FROM PUBLIC;
REVOKE ALL ON TABLE financer_fresh_rc FROM postgres;
GRANT ALL ON TABLE financer_fresh_rc TO postgres;
GRANT ALL ON TABLE financer_fresh_rc TO rtamigprod;
GRANT SELECT ON TABLE financer_fresh_rc TO prod_read_user;


--
-- Name: financer_fresh_rc_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE financer_fresh_rc_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE financer_fresh_rc_seq FROM postgres;
GRANT ALL ON SEQUENCE financer_fresh_rc_seq TO postgres;
GRANT ALL ON SEQUENCE financer_fresh_rc_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE financer_fresh_rc_seq TO prod_read_user;


--
-- Name: financer_master; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE financer_master FROM PUBLIC;
REVOKE ALL ON TABLE financer_master FROM postgres;
GRANT ALL ON TABLE financer_master TO postgres;
GRANT ALL ON TABLE financer_master TO rtamigprod;
GRANT SELECT ON TABLE financer_master TO prod_read_user;


--
-- Name: financer_seized_vehicles; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE financer_seized_vehicles FROM PUBLIC;
REVOKE ALL ON TABLE financer_seized_vehicles FROM postgres;
GRANT ALL ON TABLE financer_seized_vehicles TO postgres;
GRANT ALL ON TABLE financer_seized_vehicles TO rtamigprod;
GRANT SELECT ON TABLE financer_seized_vehicles TO prod_read_user;


--
-- Name: financer_seized_vehicles_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE financer_seized_vehicles_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE financer_seized_vehicles_seq FROM postgres;
GRANT ALL ON SEQUENCE financer_seized_vehicles_seq TO postgres;
GRANT ALL ON SEQUENCE financer_seized_vehicles_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE financer_seized_vehicles_seq TO prod_read_user;


--
-- Name: fitness_cert_details; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE fitness_cert_details FROM PUBLIC;
REVOKE ALL ON TABLE fitness_cert_details FROM postgres;
GRANT ALL ON TABLE fitness_cert_details TO postgres;
GRANT ALL ON TABLE fitness_cert_details TO rtamigprod;
GRANT SELECT ON TABLE fitness_cert_details TO prod_read_user;


--
-- Name: fitness_cert_details_history; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE fitness_cert_details_history FROM PUBLIC;
REVOKE ALL ON TABLE fitness_cert_details_history FROM postgres;
GRANT ALL ON TABLE fitness_cert_details_history TO postgres;
GRANT ALL ON TABLE fitness_cert_details_history TO rtamigprod;
GRANT SELECT ON TABLE fitness_cert_details_history TO prod_read_user;


--
-- Name: fitness_fee_detail; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE fitness_fee_detail FROM PUBLIC;
REVOKE ALL ON TABLE fitness_fee_detail FROM postgres;
GRANT ALL ON TABLE fitness_fee_detail TO postgres;
GRANT ALL ON TABLE fitness_fee_detail TO rtamigprod;
GRANT SELECT ON TABLE fitness_fee_detail TO prod_read_user;


--
-- Name: fitness_fee_detail_history; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE fitness_fee_detail_history FROM PUBLIC;
REVOKE ALL ON TABLE fitness_fee_detail_history FROM postgres;
GRANT ALL ON TABLE fitness_fee_detail_history TO postgres;
GRANT ALL ON TABLE fitness_fee_detail_history TO rtamigprod;
GRANT SELECT ON TABLE fitness_fee_detail_history TO prod_read_user;


--
-- Name: fitness_fee_details; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE fitness_fee_details FROM PUBLIC;
REVOKE ALL ON TABLE fitness_fee_details FROM postgres;
GRANT ALL ON TABLE fitness_fee_details TO postgres;
GRANT ALL ON TABLE fitness_fee_details TO rtamigprod;
GRANT SELECT ON TABLE fitness_fee_details TO prod_read_user;


--
-- Name: fitness_fee_history_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE fitness_fee_history_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE fitness_fee_history_seq FROM postgres;
GRANT ALL ON SEQUENCE fitness_fee_history_seq TO postgres;
GRANT ALL ON SEQUENCE fitness_fee_history_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE fitness_fee_history_seq TO prod_read_user;


--
-- Name: fitness_fee_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE fitness_fee_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE fitness_fee_seq FROM postgres;
GRANT ALL ON SEQUENCE fitness_fee_seq TO postgres;
GRANT ALL ON SEQUENCE fitness_fee_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE fitness_fee_seq TO prod_read_user;


--
-- Name: fitness_history_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE fitness_history_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE fitness_history_seq FROM postgres;
GRANT ALL ON SEQUENCE fitness_history_seq TO postgres;
GRANT ALL ON SEQUENCE fitness_history_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE fitness_history_seq TO prod_read_user;


--
-- Name: fitness_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE fitness_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE fitness_seq FROM postgres;
GRANT ALL ON SEQUENCE fitness_seq TO postgres;
GRANT ALL ON SEQUENCE fitness_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE fitness_seq TO prod_read_user;


--
-- Name: green_dtl_hist_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE green_dtl_hist_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE green_dtl_hist_seq FROM postgres;
GRANT ALL ON SEQUENCE green_dtl_hist_seq TO postgres;
GRANT ALL ON SEQUENCE green_dtl_hist_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE green_dtl_hist_seq TO prod_read_user;


--
-- Name: green_tax_details; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE green_tax_details FROM PUBLIC;
REVOKE ALL ON TABLE green_tax_details FROM postgres;
GRANT ALL ON TABLE green_tax_details TO postgres;
GRANT ALL ON TABLE green_tax_details TO rtamigprod;
GRANT SELECT ON TABLE green_tax_details TO prod_read_user;


--
-- Name: green_tax_details_history; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE green_tax_details_history FROM PUBLIC;
REVOKE ALL ON TABLE green_tax_details_history FROM postgres;
GRANT ALL ON TABLE green_tax_details_history TO postgres;
GRANT ALL ON TABLE green_tax_details_history TO rtamigprod;
GRANT SELECT ON TABLE green_tax_details_history TO prod_read_user;


--
-- Name: green_tax_seq; Type: ACL; Schema: public; Owner: rtamigprod
--

REVOKE ALL ON SEQUENCE green_tax_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE green_tax_seq FROM rtamigprod;
GRANT ALL ON SEQUENCE green_tax_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE green_tax_seq TO prod_read_user;


--
-- Name: hazardous_vehicle_driving_institute_users; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE hazardous_vehicle_driving_institute_users FROM PUBLIC;
REVOKE ALL ON TABLE hazardous_vehicle_driving_institute_users FROM postgres;
GRANT ALL ON TABLE hazardous_vehicle_driving_institute_users TO postgres;
GRANT ALL ON TABLE hazardous_vehicle_driving_institute_users TO rtamigprod;
GRANT SELECT ON TABLE hazardous_vehicle_driving_institute_users TO prod_read_user;


--
-- Name: hazardous_vehicle_driving_institute_users_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE hazardous_vehicle_driving_institute_users_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE hazardous_vehicle_driving_institute_users_seq FROM postgres;
GRANT ALL ON SEQUENCE hazardous_vehicle_driving_institute_users_seq TO postgres;
GRANT ALL ON SEQUENCE hazardous_vehicle_driving_institute_users_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE hazardous_vehicle_driving_institute_users_seq TO prod_read_user;


--
-- Name: hibernate_sequence; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE hibernate_sequence FROM PUBLIC;
REVOKE ALL ON SEQUENCE hibernate_sequence FROM postgres;
GRANT ALL ON SEQUENCE hibernate_sequence TO postgres;
GRANT ALL ON SEQUENCE hibernate_sequence TO rtamigprod;
GRANT SELECT ON SEQUENCE hibernate_sequence TO prod_read_user;


--
-- Name: home_tax; Type: ACL; Schema: public; Owner: rtamigprod
--

REVOKE ALL ON TABLE home_tax FROM PUBLIC;
REVOKE ALL ON TABLE home_tax FROM rtamigprod;
GRANT ALL ON TABLE home_tax TO rtamigprod;
GRANT ALL ON TABLE home_tax TO postgres;
GRANT SELECT ON TABLE home_tax TO prod_read_user;


--
-- Name: home_tax_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE home_tax_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE home_tax_seq FROM postgres;
GRANT ALL ON SEQUENCE home_tax_seq TO postgres;
GRANT ALL ON SEQUENCE home_tax_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE home_tax_seq TO prod_read_user;


--
-- Name: hsrp_detail; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE hsrp_detail FROM PUBLIC;
REVOKE ALL ON TABLE hsrp_detail FROM postgres;
GRANT ALL ON TABLE hsrp_detail TO postgres;
GRANT ALL ON TABLE hsrp_detail TO rtamigprod;
GRANT SELECT ON TABLE hsrp_detail TO prod_read_user;


--
-- Name: hsrp_detail_hist_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE hsrp_detail_hist_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE hsrp_detail_hist_seq FROM postgres;
GRANT ALL ON SEQUENCE hsrp_detail_hist_seq TO postgres;
GRANT ALL ON SEQUENCE hsrp_detail_hist_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE hsrp_detail_hist_seq TO prod_read_user;


--
-- Name: hsrp_detail_history; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE hsrp_detail_history FROM PUBLIC;
REVOKE ALL ON TABLE hsrp_detail_history FROM postgres;
GRANT ALL ON TABLE hsrp_detail_history TO postgres;
GRANT ALL ON TABLE hsrp_detail_history TO rtamigprod;
GRANT SELECT ON TABLE hsrp_detail_history TO prod_read_user;


--
-- Name: hsrp_detail_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE hsrp_detail_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE hsrp_detail_seq FROM postgres;
GRANT ALL ON SEQUENCE hsrp_detail_seq TO postgres;
GRANT ALL ON SEQUENCE hsrp_detail_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE hsrp_detail_seq TO prod_read_user;


--
-- Name: hsrp_fee_details; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE hsrp_fee_details FROM PUBLIC;
REVOKE ALL ON TABLE hsrp_fee_details FROM postgres;
GRANT ALL ON TABLE hsrp_fee_details TO postgres;
GRANT ALL ON TABLE hsrp_fee_details TO rtamigprod;
GRANT SELECT ON TABLE hsrp_fee_details TO prod_read_user;


--
-- Name: hsrp_fee_details_history; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE hsrp_fee_details_history FROM PUBLIC;
REVOKE ALL ON TABLE hsrp_fee_details_history FROM postgres;
GRANT ALL ON TABLE hsrp_fee_details_history TO postgres;
GRANT ALL ON TABLE hsrp_fee_details_history TO rtamigprod;
GRANT SELECT ON TABLE hsrp_fee_details_history TO prod_read_user;


--
-- Name: hsrp_fee_dtl_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE hsrp_fee_dtl_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE hsrp_fee_dtl_seq FROM postgres;
GRANT ALL ON SEQUENCE hsrp_fee_dtl_seq TO postgres;
GRANT ALL ON SEQUENCE hsrp_fee_dtl_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE hsrp_fee_dtl_seq TO prod_read_user;


--
-- Name: hsrp_hist_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE hsrp_hist_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE hsrp_hist_seq FROM postgres;
GRANT ALL ON SEQUENCE hsrp_hist_seq TO postgres;
GRANT ALL ON SEQUENCE hsrp_hist_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE hsrp_hist_seq TO prod_read_user;


--
-- Name: hsrp_trans_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE hsrp_trans_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE hsrp_trans_seq FROM postgres;
GRANT ALL ON SEQUENCE hsrp_trans_seq TO postgres;
GRANT ALL ON SEQUENCE hsrp_trans_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE hsrp_trans_seq TO prod_read_user;


--
-- Name: hsrp_transaction; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE hsrp_transaction FROM PUBLIC;
REVOKE ALL ON TABLE hsrp_transaction FROM postgres;
GRANT ALL ON TABLE hsrp_transaction TO postgres;
GRANT ALL ON TABLE hsrp_transaction TO rtamigprod;
GRANT SELECT ON TABLE hsrp_transaction TO prod_read_user;


--
-- Name: insurance_details; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE insurance_details FROM PUBLIC;
REVOKE ALL ON TABLE insurance_details FROM postgres;
GRANT ALL ON TABLE insurance_details TO postgres;
GRANT ALL ON TABLE insurance_details TO rtamigprod;
GRANT SELECT ON TABLE insurance_details TO prod_read_user;


--
-- Name: insurance_dtl_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE insurance_dtl_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE insurance_dtl_seq FROM postgres;
GRANT ALL ON SEQUENCE insurance_dtl_seq TO postgres;
GRANT ALL ON SEQUENCE insurance_dtl_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE insurance_dtl_seq TO prod_read_user;


--
-- Name: insurance_type; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE insurance_type FROM PUBLIC;
REVOKE ALL ON TABLE insurance_type FROM postgres;
GRANT ALL ON TABLE insurance_type TO postgres;
GRANT ALL ON TABLE insurance_type TO rtamigprod;
GRANT SELECT ON TABLE insurance_type TO prod_read_user;


--
-- Name: insurance_type_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE insurance_type_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE insurance_type_seq FROM postgres;
GRANT ALL ON SEQUENCE insurance_type_seq TO postgres;
GRANT ALL ON SEQUENCE insurance_type_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE insurance_type_seq TO prod_read_user;


--
-- Name: late_fee_details; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE late_fee_details FROM PUBLIC;
REVOKE ALL ON TABLE late_fee_details FROM postgres;
GRANT ALL ON TABLE late_fee_details TO postgres;
GRANT ALL ON TABLE late_fee_details TO rtamigprod;
GRANT SELECT ON TABLE late_fee_details TO prod_read_user;


--
-- Name: late_fee_details_history; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE late_fee_details_history FROM PUBLIC;
REVOKE ALL ON TABLE late_fee_details_history FROM postgres;
GRANT ALL ON TABLE late_fee_details_history TO postgres;
GRANT ALL ON TABLE late_fee_details_history TO rtamigprod;
GRANT SELECT ON TABLE late_fee_details_history TO prod_read_user;


--
-- Name: late_fee_details_history_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE late_fee_details_history_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE late_fee_details_history_seq FROM postgres;
GRANT ALL ON SEQUENCE late_fee_details_history_seq TO postgres;
GRANT ALL ON SEQUENCE late_fee_details_history_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE late_fee_details_history_seq TO prod_read_user;


--
-- Name: late_fee_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE late_fee_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE late_fee_seq FROM postgres;
GRANT ALL ON SEQUENCE late_fee_seq TO postgres;
GRANT ALL ON SEQUENCE late_fee_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE late_fee_seq TO prod_read_user;


--
-- Name: learners_permit_dtls; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE learners_permit_dtls FROM PUBLIC;
REVOKE ALL ON TABLE learners_permit_dtls FROM postgres;
GRANT ALL ON TABLE learners_permit_dtls TO postgres;
GRANT ALL ON TABLE learners_permit_dtls TO rtamigprod;
GRANT SELECT ON TABLE learners_permit_dtls TO prod_read_user;


--
-- Name: learners_permit_dtls_history; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE learners_permit_dtls_history FROM PUBLIC;
REVOKE ALL ON TABLE learners_permit_dtls_history FROM postgres;
GRANT ALL ON TABLE learners_permit_dtls_history TO postgres;
GRANT ALL ON TABLE learners_permit_dtls_history TO rtamigprod;
GRANT SELECT ON TABLE learners_permit_dtls_history TO prod_read_user;


--
-- Name: learners_permit_hist_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE learners_permit_hist_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE learners_permit_hist_seq FROM postgres;
GRANT ALL ON SEQUENCE learners_permit_hist_seq TO postgres;
GRANT ALL ON SEQUENCE learners_permit_hist_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE learners_permit_hist_seq TO prod_read_user;


--
-- Name: legal_hier_details; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE legal_hier_details FROM PUBLIC;
REVOKE ALL ON TABLE legal_hier_details FROM postgres;
GRANT ALL ON TABLE legal_hier_details TO postgres;
GRANT ALL ON TABLE legal_hier_details TO rtamigprod;
GRANT SELECT ON TABLE legal_hier_details TO prod_read_user;


--
-- Name: legal_hier_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE legal_hier_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE legal_hier_seq FROM postgres;
GRANT ALL ON SEQUENCE legal_hier_seq TO postgres;
GRANT ALL ON SEQUENCE legal_hier_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE legal_hier_seq TO prod_read_user;


--
-- Name: licence_attachment_details; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE licence_attachment_details FROM PUBLIC;
REVOKE ALL ON TABLE licence_attachment_details FROM postgres;
GRANT ALL ON TABLE licence_attachment_details TO postgres;
GRANT ALL ON TABLE licence_attachment_details TO rtamigprod;
GRANT SELECT ON TABLE licence_attachment_details TO prod_read_user;


--
-- Name: licence_attachment_details_sequence; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE licence_attachment_details_sequence FROM PUBLIC;
REVOKE ALL ON SEQUENCE licence_attachment_details_sequence FROM postgres;
GRANT ALL ON SEQUENCE licence_attachment_details_sequence TO postgres;
GRANT ALL ON SEQUENCE licence_attachment_details_sequence TO rtamigprod;
GRANT SELECT ON SEQUENCE licence_attachment_details_sequence TO prod_read_user;


--
-- Name: licence_holder_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE licence_holder_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE licence_holder_seq FROM postgres;
GRANT ALL ON SEQUENCE licence_holder_seq TO postgres;
GRANT ALL ON SEQUENCE licence_holder_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE licence_holder_seq TO prod_read_user;


--
-- Name: licence_print_request; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE licence_print_request FROM PUBLIC;
REVOKE ALL ON TABLE licence_print_request FROM postgres;
GRANT ALL ON TABLE licence_print_request TO postgres;
GRANT ALL ON TABLE licence_print_request TO rtamigprod;
GRANT SELECT ON TABLE licence_print_request TO prod_read_user;


--
-- Name: license_holder_dtls; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE license_holder_dtls FROM PUBLIC;
REVOKE ALL ON TABLE license_holder_dtls FROM postgres;
GRANT ALL ON TABLE license_holder_dtls TO postgres;
GRANT ALL ON TABLE license_holder_dtls TO rtamigprod;
GRANT SELECT ON TABLE license_holder_dtls TO prod_read_user;


--
-- Name: license_holder_txns; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE license_holder_txns FROM PUBLIC;
REVOKE ALL ON TABLE license_holder_txns FROM postgres;
GRANT ALL ON TABLE license_holder_txns TO postgres;
GRANT ALL ON TABLE license_holder_txns TO rtamigprod;
GRANT SELECT ON TABLE license_holder_txns TO prod_read_user;


--
-- Name: license_identities_details; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE license_identities_details FROM PUBLIC;
REVOKE ALL ON TABLE license_identities_details FROM postgres;
GRANT ALL ON TABLE license_identities_details TO postgres;
GRANT ALL ON TABLE license_identities_details TO rtamigprod;
GRANT SELECT ON TABLE license_identities_details TO prod_read_user;


--
-- Name: license_identities_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE license_identities_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE license_identities_seq FROM postgres;
GRANT ALL ON SEQUENCE license_identities_seq TO postgres;
GRANT ALL ON SEQUENCE license_identities_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE license_identities_seq TO prod_read_user;


--
-- Name: license_idp_dtls; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE license_idp_dtls FROM PUBLIC;
REVOKE ALL ON TABLE license_idp_dtls FROM postgres;
GRANT ALL ON TABLE license_idp_dtls TO postgres;
GRANT ALL ON TABLE license_idp_dtls TO rtamigprod;
GRANT SELECT ON TABLE license_idp_dtls TO prod_read_user;


--
-- Name: license_vehicle_class_ref; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE license_vehicle_class_ref FROM PUBLIC;
REVOKE ALL ON TABLE license_vehicle_class_ref FROM postgres;
GRANT ALL ON TABLE license_vehicle_class_ref TO postgres;
GRANT ALL ON TABLE license_vehicle_class_ref TO rtamigprod;
GRANT SELECT ON TABLE license_vehicle_class_ref TO prod_read_user;


--
-- Name: life_tax_details; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE life_tax_details FROM PUBLIC;
REVOKE ALL ON TABLE life_tax_details FROM postgres;
GRANT ALL ON TABLE life_tax_details TO postgres;
GRANT ALL ON TABLE life_tax_details TO rtamigprod;
GRANT SELECT ON TABLE life_tax_details TO prod_read_user;


--
-- Name: life_tax_details_dup; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE life_tax_details_dup FROM PUBLIC;
REVOKE ALL ON TABLE life_tax_details_dup FROM postgres;
GRANT ALL ON TABLE life_tax_details_dup TO postgres;
GRANT ALL ON TABLE life_tax_details_dup TO rtamigprod;
GRANT SELECT ON TABLE life_tax_details_dup TO prod_read_user;


--
-- Name: life_tax_details_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE life_tax_details_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE life_tax_details_seq FROM postgres;
GRANT ALL ON SEQUENCE life_tax_details_seq TO postgres;
GRANT ALL ON SEQUENCE life_tax_details_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE life_tax_details_seq TO prod_read_user;


--
-- Name: lifetaxupd; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE lifetaxupd FROM PUBLIC;
REVOKE ALL ON TABLE lifetaxupd FROM postgres;
GRANT ALL ON TABLE lifetaxupd TO postgres;
GRANT ALL ON TABLE lifetaxupd TO rtamigprod;
GRANT SELECT ON TABLE lifetaxupd TO prod_read_user;


--
-- Name: login_track; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE login_track FROM PUBLIC;
REVOKE ALL ON TABLE login_track FROM postgres;
GRANT ALL ON TABLE login_track TO postgres;
GRANT ALL ON TABLE login_track TO rtamigprod;
GRANT SELECT ON TABLE login_track TO prod_read_user;


--
-- Name: login_track_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE login_track_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE login_track_seq FROM postgres;
GRANT ALL ON SEQUENCE login_track_seq TO postgres;
GRANT ALL ON SEQUENCE login_track_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE login_track_seq TO prod_read_user;


--
-- Name: maker_master; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE maker_master FROM PUBLIC;
REVOKE ALL ON TABLE maker_master FROM postgres;
GRANT ALL ON TABLE maker_master TO postgres;
GRANT ALL ON TABLE maker_master TO rtamigprod;
GRANT SELECT ON TABLE maker_master TO prod_read_user;


--
-- Name: maker_master_sequence; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE maker_master_sequence FROM PUBLIC;
REVOKE ALL ON SEQUENCE maker_master_sequence FROM postgres;
GRANT ALL ON SEQUENCE maker_master_sequence TO postgres;
GRANT ALL ON SEQUENCE maker_master_sequence TO rtamigprod;
GRANT SELECT ON SEQUENCE maker_master_sequence TO prod_read_user;


--
-- Name: mandal; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE mandal FROM PUBLIC;
REVOKE ALL ON TABLE mandal FROM postgres;
GRANT ALL ON TABLE mandal TO postgres;
GRANT ALL ON TABLE mandal TO rtamigprod;
GRANT SELECT ON TABLE mandal TO prod_read_user;


--
-- Name: mandal_id_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE mandal_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE mandal_id_seq FROM postgres;
GRANT ALL ON SEQUENCE mandal_id_seq TO postgres;
GRANT ALL ON SEQUENCE mandal_id_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE mandal_id_seq TO prod_read_user;


--
-- Name: medical_practitioner_user_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE medical_practitioner_user_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE medical_practitioner_user_seq FROM postgres;
GRANT ALL ON SEQUENCE medical_practitioner_user_seq TO postgres;
GRANT ALL ON SEQUENCE medical_practitioner_user_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE medical_practitioner_user_seq TO prod_read_user;


--
-- Name: medical_practitioner_users; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE medical_practitioner_users FROM PUBLIC;
REVOKE ALL ON TABLE medical_practitioner_users FROM postgres;
GRANT ALL ON TABLE medical_practitioner_users TO postgres;
GRANT ALL ON TABLE medical_practitioner_users TO rtamigprod;
GRANT SELECT ON TABLE medical_practitioner_users TO prod_read_user;


--
-- Name: model_details; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE model_details FROM PUBLIC;
REVOKE ALL ON TABLE model_details FROM postgres;
GRANT ALL ON TABLE model_details TO postgres;
GRANT ALL ON TABLE model_details TO rtamigprod;
GRANT SELECT ON TABLE model_details TO prod_read_user;


--
-- Name: model_details_sequence; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE model_details_sequence FROM PUBLIC;
REVOKE ALL ON SEQUENCE model_details_sequence FROM postgres;
GRANT ALL ON SEQUENCE model_details_sequence TO postgres;
GRANT ALL ON SEQUENCE model_details_sequence TO rtamigprod;
GRANT SELECT ON SEQUENCE model_details_sequence TO prod_read_user;


--
-- Name: neigh_district_mapping; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE neigh_district_mapping FROM PUBLIC;
REVOKE ALL ON TABLE neigh_district_mapping FROM postgres;
GRANT ALL ON TABLE neigh_district_mapping TO postgres;
GRANT ALL ON TABLE neigh_district_mapping TO rtamigprod;
GRANT SELECT ON TABLE neigh_district_mapping TO prod_read_user;


--
-- Name: neigh_district_master; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE neigh_district_master FROM PUBLIC;
REVOKE ALL ON TABLE neigh_district_master FROM postgres;
GRANT ALL ON TABLE neigh_district_master TO postgres;
GRANT ALL ON TABLE neigh_district_master TO rtamigprod;
GRANT SELECT ON TABLE neigh_district_master TO prod_read_user;


--
-- Name: neigh_state_master; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE neigh_state_master FROM PUBLIC;
REVOKE ALL ON TABLE neigh_state_master FROM postgres;
GRANT ALL ON TABLE neigh_state_master TO postgres;
GRANT ALL ON TABLE neigh_state_master TO rtamigprod;
GRANT SELECT ON TABLE neigh_state_master TO prod_read_user;


--
-- Name: new_vehicle_rc_migration; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE new_vehicle_rc_migration FROM PUBLIC;
REVOKE ALL ON TABLE new_vehicle_rc_migration FROM postgres;
GRANT ALL ON TABLE new_vehicle_rc_migration TO postgres;
GRANT ALL ON TABLE new_vehicle_rc_migration TO rtamigprod;
GRANT SELECT ON TABLE new_vehicle_rc_migration TO prod_read_user;


--
-- Name: newvechilepolicy; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE newvechilepolicy FROM PUBLIC;
REVOKE ALL ON TABLE newvechilepolicy FROM postgres;
GRANT ALL ON TABLE newvechilepolicy TO postgres;
GRANT ALL ON TABLE newvechilepolicy TO rtamigprod;
GRANT SELECT ON TABLE newvechilepolicy TO prod_read_user;


--
-- Name: noc_address_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE noc_address_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE noc_address_seq FROM postgres;
GRANT ALL ON SEQUENCE noc_address_seq TO postgres;
GRANT ALL ON SEQUENCE noc_address_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE noc_address_seq TO prod_read_user;


--
-- Name: noc_details_history_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE noc_details_history_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE noc_details_history_seq FROM postgres;
GRANT ALL ON SEQUENCE noc_details_history_seq TO postgres;
GRANT ALL ON SEQUENCE noc_details_history_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE noc_details_history_seq TO prod_read_user;


--
-- Name: noc_details_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE noc_details_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE noc_details_seq FROM postgres;
GRANT ALL ON SEQUENCE noc_details_seq TO postgres;
GRANT ALL ON SEQUENCE noc_details_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE noc_details_seq TO prod_read_user;


--
-- Name: ownership_type; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE ownership_type FROM PUBLIC;
REVOKE ALL ON TABLE ownership_type FROM postgres;
GRANT ALL ON TABLE ownership_type TO postgres;
GRANT ALL ON TABLE ownership_type TO rtamigprod;
GRANT SELECT ON TABLE ownership_type TO prod_read_user;


--
-- Name: ownership_type_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE ownership_type_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE ownership_type_seq FROM postgres;
GRANT ALL ON SEQUENCE ownership_type_seq TO postgres;
GRANT ALL ON SEQUENCE ownership_type_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE ownership_type_seq TO prod_read_user;


--
-- Name: p_address_history_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE p_address_history_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE p_address_history_seq FROM postgres;
GRANT ALL ON SEQUENCE p_address_history_seq TO postgres;
GRANT ALL ON SEQUENCE p_address_history_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE p_address_history_seq TO prod_read_user;


--
-- Name: p_address_not_matched; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE p_address_not_matched FROM PUBLIC;
REVOKE ALL ON TABLE p_address_not_matched FROM postgres;
GRANT ALL ON TABLE p_address_not_matched TO postgres;
GRANT ALL ON TABLE p_address_not_matched TO rtamigprod;
GRANT SELECT ON TABLE p_address_not_matched TO prod_read_user;


--
-- Name: p_address_not_matched_id_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE p_address_not_matched_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE p_address_not_matched_id_seq FROM postgres;
GRANT ALL ON SEQUENCE p_address_not_matched_id_seq TO postgres;
GRANT ALL ON SEQUENCE p_address_not_matched_id_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE p_address_not_matched_id_seq TO prod_read_user;


--
-- Name: p_address_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE p_address_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE p_address_seq FROM postgres;
GRANT ALL ON SEQUENCE p_address_seq TO postgres;
GRANT ALL ON SEQUENCE p_address_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE p_address_seq TO prod_read_user;


--
-- Name: periodic_tax_details; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE periodic_tax_details FROM PUBLIC;
REVOKE ALL ON TABLE periodic_tax_details FROM postgres;
GRANT ALL ON TABLE periodic_tax_details TO postgres;
GRANT ALL ON TABLE periodic_tax_details TO rtamigprod;
GRANT SELECT ON TABLE periodic_tax_details TO prod_read_user;


--
-- Name: periodic_tax_details_dup; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE periodic_tax_details_dup FROM PUBLIC;
REVOKE ALL ON TABLE periodic_tax_details_dup FROM postgres;
GRANT ALL ON TABLE periodic_tax_details_dup TO postgres;
GRANT ALL ON TABLE periodic_tax_details_dup TO rtamigprod;
GRANT SELECT ON TABLE periodic_tax_details_dup TO prod_read_user;


--
-- Name: periodic_tax_details_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE periodic_tax_details_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE periodic_tax_details_seq FROM postgres;
GRANT ALL ON SEQUENCE periodic_tax_details_seq TO postgres;
GRANT ALL ON SEQUENCE periodic_tax_details_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE periodic_tax_details_seq TO prod_read_user;


--
-- Name: permanent_address; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE permanent_address FROM PUBLIC;
REVOKE ALL ON TABLE permanent_address FROM postgres;
GRANT ALL ON TABLE permanent_address TO postgres;
GRANT ALL ON TABLE permanent_address TO rtamigprod;
GRANT SELECT ON TABLE permanent_address TO prod_read_user;


--
-- Name: permanent_address_history; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE permanent_address_history FROM PUBLIC;
REVOKE ALL ON TABLE permanent_address_history FROM postgres;
GRANT ALL ON TABLE permanent_address_history TO postgres;
GRANT ALL ON TABLE permanent_address_history TO rtamigprod;
GRANT SELECT ON TABLE permanent_address_history TO prod_read_user;


--
-- Name: permit_allowed; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE permit_allowed FROM PUBLIC;
REVOKE ALL ON TABLE permit_allowed FROM postgres;
GRANT ALL ON TABLE permit_allowed TO postgres;
GRANT ALL ON TABLE permit_allowed TO rtamigprod;
GRANT SELECT ON TABLE permit_allowed TO prod_read_user;


--
-- Name: permit_allowed_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE permit_allowed_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE permit_allowed_seq FROM postgres;
GRANT ALL ON SEQUENCE permit_allowed_seq TO postgres;
GRANT ALL ON SEQUENCE permit_allowed_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE permit_allowed_seq TO prod_read_user;


--
-- Name: permit_auth_card_details; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE permit_auth_card_details FROM PUBLIC;
REVOKE ALL ON TABLE permit_auth_card_details FROM postgres;
GRANT ALL ON TABLE permit_auth_card_details TO postgres;
GRANT ALL ON TABLE permit_auth_card_details TO rtamigprod;
GRANT SELECT ON TABLE permit_auth_card_details TO prod_read_user;


--
-- Name: permit_auth_card_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE permit_auth_card_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE permit_auth_card_seq FROM postgres;
GRANT ALL ON SEQUENCE permit_auth_card_seq TO postgres;
GRANT ALL ON SEQUENCE permit_auth_card_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE permit_auth_card_seq TO prod_read_user;


--
-- Name: permit_conditions_master; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE permit_conditions_master FROM PUBLIC;
REVOKE ALL ON TABLE permit_conditions_master FROM postgres;
GRANT ALL ON TABLE permit_conditions_master TO postgres;
GRANT ALL ON TABLE permit_conditions_master TO rtamigprod;
GRANT SELECT ON TABLE permit_conditions_master TO prod_read_user;


--
-- Name: permit_conditions_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE permit_conditions_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE permit_conditions_seq FROM postgres;
GRANT ALL ON SEQUENCE permit_conditions_seq TO postgres;
GRANT ALL ON SEQUENCE permit_conditions_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE permit_conditions_seq TO prod_read_user;


--
-- Name: permit_details_mapping; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE permit_details_mapping FROM PUBLIC;
REVOKE ALL ON TABLE permit_details_mapping FROM postgres;
GRANT ALL ON TABLE permit_details_mapping TO postgres;
GRANT ALL ON TABLE permit_details_mapping TO rtamigprod;
GRANT SELECT ON TABLE permit_details_mapping TO prod_read_user;


--
-- Name: permit_details_mapping_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE permit_details_mapping_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE permit_details_mapping_seq FROM postgres;
GRANT ALL ON SEQUENCE permit_details_mapping_seq TO postgres;
GRANT ALL ON SEQUENCE permit_details_mapping_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE permit_details_mapping_seq TO prod_read_user;


--
-- Name: permit_fee_detail; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE permit_fee_detail FROM PUBLIC;
REVOKE ALL ON TABLE permit_fee_detail FROM postgres;
GRANT ALL ON TABLE permit_fee_detail TO postgres;
GRANT ALL ON TABLE permit_fee_detail TO rtamigprod;
GRANT SELECT ON TABLE permit_fee_detail TO prod_read_user;


--
-- Name: permit_fee_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE permit_fee_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE permit_fee_seq FROM postgres;
GRANT ALL ON SEQUENCE permit_fee_seq TO postgres;
GRANT ALL ON SEQUENCE permit_fee_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE permit_fee_seq TO prod_read_user;


--
-- Name: permit_goods_master; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE permit_goods_master FROM PUBLIC;
REVOKE ALL ON TABLE permit_goods_master FROM postgres;
GRANT ALL ON TABLE permit_goods_master TO postgres;
GRANT ALL ON TABLE permit_goods_master TO rtamigprod;
GRANT SELECT ON TABLE permit_goods_master TO prod_read_user;


--
-- Name: permit_goods_master_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE permit_goods_master_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE permit_goods_master_seq FROM postgres;
GRANT ALL ON SEQUENCE permit_goods_master_seq TO postgres;
GRANT ALL ON SEQUENCE permit_goods_master_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE permit_goods_master_seq TO prod_read_user;


--
-- Name: permit_header; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE permit_header FROM PUBLIC;
REVOKE ALL ON TABLE permit_header FROM postgres;
GRANT ALL ON TABLE permit_header TO postgres;
GRANT ALL ON TABLE permit_header TO rtamigprod;
GRANT SELECT ON TABLE permit_header TO prod_read_user;


--
-- Name: permit_header_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE permit_header_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE permit_header_seq FROM postgres;
GRANT ALL ON SEQUENCE permit_header_seq TO postgres;
GRANT ALL ON SEQUENCE permit_header_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE permit_header_seq TO prod_read_user;


--
-- Name: permit_route_conditions_master; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE permit_route_conditions_master FROM PUBLIC;
REVOKE ALL ON TABLE permit_route_conditions_master FROM postgres;
GRANT ALL ON TABLE permit_route_conditions_master TO postgres;
GRANT ALL ON TABLE permit_route_conditions_master TO rtamigprod;
GRANT SELECT ON TABLE permit_route_conditions_master TO prod_read_user;


--
-- Name: permit_route_master_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE permit_route_master_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE permit_route_master_seq FROM postgres;
GRANT ALL ON SEQUENCE permit_route_master_seq TO postgres;
GRANT ALL ON SEQUENCE permit_route_master_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE permit_route_master_seq TO prod_read_user;


--
-- Name: permit_susp_dtls; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE permit_susp_dtls FROM PUBLIC;
REVOKE ALL ON TABLE permit_susp_dtls FROM postgres;
GRANT ALL ON TABLE permit_susp_dtls TO postgres;
GRANT ALL ON TABLE permit_susp_dtls TO rtamigprod;
GRANT SELECT ON TABLE permit_susp_dtls TO prod_read_user;


--
-- Name: permit_susp_dtls_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE permit_susp_dtls_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE permit_susp_dtls_seq FROM postgres;
GRANT ALL ON SEQUENCE permit_susp_dtls_seq TO postgres;
GRANT ALL ON SEQUENCE permit_susp_dtls_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE permit_susp_dtls_seq TO prod_read_user;


--
-- Name: permit_type_master; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE permit_type_master FROM PUBLIC;
REVOKE ALL ON TABLE permit_type_master FROM postgres;
GRANT ALL ON TABLE permit_type_master TO postgres;
GRANT ALL ON TABLE permit_type_master TO rtamigprod;
GRANT SELECT ON TABLE permit_type_master TO prod_read_user;


--
-- Name: permit_type_sequence; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE permit_type_sequence FROM PUBLIC;
REVOKE ALL ON SEQUENCE permit_type_sequence FROM postgres;
GRANT ALL ON SEQUENCE permit_type_sequence TO postgres;
GRANT ALL ON SEQUENCE permit_type_sequence TO rtamigprod;
GRANT SELECT ON SEQUENCE permit_type_sequence TO prod_read_user;


--
-- Name: permit_vclass_mapping_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE permit_vclass_mapping_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE permit_vclass_mapping_seq FROM postgres;
GRANT ALL ON SEQUENCE permit_vclass_mapping_seq TO postgres;
GRANT ALL ON SEQUENCE permit_vclass_mapping_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE permit_vclass_mapping_seq TO prod_read_user;


--
-- Name: permit_vehicle_class_mapping; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE permit_vehicle_class_mapping FROM PUBLIC;
REVOKE ALL ON TABLE permit_vehicle_class_mapping FROM postgres;
GRANT ALL ON TABLE permit_vehicle_class_mapping TO postgres;
GRANT ALL ON TABLE permit_vehicle_class_mapping TO rtamigprod;
GRANT SELECT ON TABLE permit_vehicle_class_mapping TO prod_read_user;


--
-- Name: post_office; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE post_office FROM PUBLIC;
REVOKE ALL ON TABLE post_office FROM postgres;
GRANT ALL ON TABLE post_office TO postgres;
GRANT ALL ON TABLE post_office TO rtamigprod;
GRANT SELECT ON TABLE post_office TO prod_read_user;


--
-- Name: post_office_id_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE post_office_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE post_office_id_seq FROM postgres;
GRANT ALL ON SEQUENCE post_office_id_seq TO postgres;
GRANT ALL ON SEQUENCE post_office_id_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE post_office_id_seq TO prod_read_user;


--
-- Name: pr_mapping; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE pr_mapping FROM PUBLIC;
REVOKE ALL ON TABLE pr_mapping FROM postgres;
GRANT ALL ON TABLE pr_mapping TO postgres;
GRANT ALL ON TABLE pr_mapping TO rtamigprod;
GRANT SELECT ON TABLE pr_mapping TO prod_read_user;


--
-- Name: pr_series_master; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE pr_series_master FROM PUBLIC;
REVOKE ALL ON TABLE pr_series_master FROM postgres;
GRANT ALL ON TABLE pr_series_master TO postgres;
GRANT ALL ON TABLE pr_series_master TO rtamigprod;
GRANT SELECT ON TABLE pr_series_master TO prod_read_user;


--
-- Name: pr_series_master_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE pr_series_master_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE pr_series_master_seq FROM postgres;
GRANT ALL ON SEQUENCE pr_series_master_seq TO postgres;
GRANT ALL ON SEQUENCE pr_series_master_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE pr_series_master_seq TO prod_read_user;


--
-- Name: primary_temp_permit_mapping; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE primary_temp_permit_mapping FROM PUBLIC;
REVOKE ALL ON TABLE primary_temp_permit_mapping FROM postgres;
GRANT ALL ON TABLE primary_temp_permit_mapping TO postgres;
GRANT ALL ON TABLE primary_temp_permit_mapping TO rtamigprod;
GRANT SELECT ON TABLE primary_temp_permit_mapping TO prod_read_user;


--
-- Name: primary_temp_permit_mapping_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE primary_temp_permit_mapping_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE primary_temp_permit_mapping_seq FROM postgres;
GRANT ALL ON SEQUENCE primary_temp_permit_mapping_seq TO postgres;
GRANT ALL ON SEQUENCE primary_temp_permit_mapping_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE primary_temp_permit_mapping_seq TO prod_read_user;


--
-- Name: print_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE print_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE print_seq FROM postgres;
GRANT ALL ON SEQUENCE print_seq TO postgres;
GRANT ALL ON SEQUENCE print_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE print_seq TO prod_read_user;


--
-- Name: puc_users; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE puc_users FROM PUBLIC;
REVOKE ALL ON TABLE puc_users FROM postgres;
GRANT ALL ON TABLE puc_users TO postgres;
GRANT ALL ON TABLE puc_users TO rtamigprod;
GRANT SELECT ON TABLE puc_users TO prod_read_user;


--
-- Name: puc_users_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE puc_users_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE puc_users_seq FROM postgres;
GRANT ALL ON SEQUENCE puc_users_seq TO postgres;
GRANT ALL ON SEQUENCE puc_users_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE puc_users_seq TO prod_read_user;


--
-- Name: qualification; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE qualification FROM PUBLIC;
REVOKE ALL ON TABLE qualification FROM postgres;
GRANT ALL ON TABLE qualification TO postgres;
GRANT ALL ON TABLE qualification TO rtamigprod;
GRANT SELECT ON TABLE qualification TO prod_read_user;


--
-- Name: qualification_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE qualification_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE qualification_seq FROM postgres;
GRANT ALL ON SEQUENCE qualification_seq TO postgres;
GRANT ALL ON SEQUENCE qualification_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE qualification_seq TO prod_read_user;


--
-- Name: rc_card_employee; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE rc_card_employee FROM PUBLIC;
REVOKE ALL ON TABLE rc_card_employee FROM postgres;
GRANT ALL ON TABLE rc_card_employee TO postgres;
GRANT ALL ON TABLE rc_card_employee TO rtamigprod;
GRANT SELECT ON TABLE rc_card_employee TO prod_read_user;


--
-- Name: rc_lock; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE rc_lock FROM PUBLIC;
REVOKE ALL ON TABLE rc_lock FROM postgres;
GRANT ALL ON TABLE rc_lock TO postgres;
GRANT ALL ON TABLE rc_lock TO rtamigprod;
GRANT SELECT ON TABLE rc_lock TO prod_read_user;


--
-- Name: rc_lock_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE rc_lock_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE rc_lock_seq FROM postgres;
GRANT ALL ON SEQUENCE rc_lock_seq TO postgres;
GRANT ALL ON SEQUENCE rc_lock_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE rc_lock_seq TO prod_read_user;


--
-- Name: rccard_employee_sequence; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE rccard_employee_sequence FROM PUBLIC;
REVOKE ALL ON SEQUENCE rccard_employee_sequence FROM postgres;
GRANT ALL ON SEQUENCE rccard_employee_sequence TO postgres;
GRANT ALL ON SEQUENCE rccard_employee_sequence TO rtamigprod;
GRANT SELECT ON SEQUENCE rccard_employee_sequence TO prod_read_user;


--
-- Name: recom_lttr_issued_dtls; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE recom_lttr_issued_dtls FROM PUBLIC;
REVOKE ALL ON TABLE recom_lttr_issued_dtls FROM postgres;
GRANT ALL ON TABLE recom_lttr_issued_dtls TO postgres;
GRANT ALL ON TABLE recom_lttr_issued_dtls TO rtamigprod;
GRANT SELECT ON TABLE recom_lttr_issued_dtls TO prod_read_user;


--
-- Name: recom_lttr_issued_dtls_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE recom_lttr_issued_dtls_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE recom_lttr_issued_dtls_seq FROM postgres;
GRANT ALL ON SEQUENCE recom_lttr_issued_dtls_seq TO postgres;
GRANT ALL ON SEQUENCE recom_lttr_issued_dtls_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE recom_lttr_issued_dtls_seq TO prod_read_user;


--
-- Name: reg_fee_detail; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE reg_fee_detail FROM PUBLIC;
REVOKE ALL ON TABLE reg_fee_detail FROM postgres;
GRANT ALL ON TABLE reg_fee_detail TO postgres;
GRANT ALL ON TABLE reg_fee_detail TO rtamigprod;
GRANT SELECT ON TABLE reg_fee_detail TO prod_read_user;


--
-- Name: reg_fee_detail_history; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE reg_fee_detail_history FROM PUBLIC;
REVOKE ALL ON TABLE reg_fee_detail_history FROM postgres;
GRANT ALL ON TABLE reg_fee_detail_history TO postgres;
GRANT ALL ON TABLE reg_fee_detail_history TO rtamigprod;
GRANT SELECT ON TABLE reg_fee_detail_history TO prod_read_user;


--
-- Name: reg_fee_details; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE reg_fee_details FROM PUBLIC;
REVOKE ALL ON TABLE reg_fee_details FROM postgres;
GRANT ALL ON TABLE reg_fee_details TO postgres;
GRANT ALL ON TABLE reg_fee_details TO rtamigprod;
GRANT SELECT ON TABLE reg_fee_details TO prod_read_user;


--
-- Name: reg_fee_dtl_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE reg_fee_dtl_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE reg_fee_dtl_seq FROM postgres;
GRANT ALL ON SEQUENCE reg_fee_dtl_seq TO postgres;
GRANT ALL ON SEQUENCE reg_fee_dtl_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE reg_fee_dtl_seq TO prod_read_user;


--
-- Name: reg_fee_hist_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE reg_fee_hist_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE reg_fee_hist_seq FROM postgres;
GRANT ALL ON SEQUENCE reg_fee_hist_seq TO postgres;
GRANT ALL ON SEQUENCE reg_fee_hist_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE reg_fee_hist_seq TO prod_read_user;


--
-- Name: reg_fee_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE reg_fee_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE reg_fee_seq FROM postgres;
GRANT ALL ON SEQUENCE reg_fee_seq TO postgres;
GRANT ALL ON SEQUENCE reg_fee_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE reg_fee_seq TO prod_read_user;


--
-- Name: registration_category; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE registration_category FROM PUBLIC;
REVOKE ALL ON TABLE registration_category FROM postgres;
GRANT ALL ON TABLE registration_category TO postgres;
GRANT ALL ON TABLE registration_category TO rtamigprod;
GRANT SELECT ON TABLE registration_category TO prod_read_user;


--
-- Name: registration_category_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE registration_category_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE registration_category_seq FROM postgres;
GRANT ALL ON SEQUENCE registration_category_seq TO postgres;
GRANT ALL ON SEQUENCE registration_category_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE registration_category_seq TO prod_read_user;


--
-- Name: rejection_history; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE rejection_history FROM PUBLIC;
REVOKE ALL ON TABLE rejection_history FROM postgres;
GRANT ALL ON TABLE rejection_history TO postgres;
GRANT ALL ON TABLE rejection_history TO rtamigprod;
GRANT SELECT ON TABLE rejection_history TO prod_read_user;


--
-- Name: rejection_history_sequence; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE rejection_history_sequence FROM PUBLIC;
REVOKE ALL ON SEQUENCE rejection_history_sequence FROM postgres;
GRANT ALL ON SEQUENCE rejection_history_sequence TO postgres;
GRANT ALL ON SEQUENCE rejection_history_sequence TO rtamigprod;
GRANT SELECT ON SEQUENCE rejection_history_sequence TO prod_read_user;


--
-- Name: reset_password_log; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE reset_password_log FROM PUBLIC;
REVOKE ALL ON TABLE reset_password_log FROM postgres;
GRANT ALL ON TABLE reset_password_log TO postgres;
GRANT ALL ON TABLE reset_password_log TO rtamigprod;
GRANT SELECT ON TABLE reset_password_log TO prod_read_user;


--
-- Name: reset_pwd_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE reset_pwd_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE reset_pwd_seq FROM postgres;
GRANT ALL ON SEQUENCE reset_pwd_seq TO postgres;
GRANT ALL ON SEQUENCE reset_pwd_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE reset_pwd_seq TO prod_read_user;


--
-- Name: role_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE role_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE role_seq FROM postgres;
GRANT ALL ON SEQUENCE role_seq TO postgres;
GRANT ALL ON SEQUENCE role_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE role_seq TO prod_read_user;


--
-- Name: route_details_master; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE route_details_master FROM PUBLIC;
REVOKE ALL ON TABLE route_details_master FROM postgres;
GRANT ALL ON TABLE route_details_master TO postgres;
GRANT ALL ON TABLE route_details_master TO rtamigprod;
GRANT SELECT ON TABLE route_details_master TO prod_read_user;


--
-- Name: route_master; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE route_master FROM PUBLIC;
REVOKE ALL ON TABLE route_master FROM postgres;
GRANT ALL ON TABLE route_master TO postgres;
GRANT ALL ON TABLE route_master TO rtamigprod;
GRANT SELECT ON TABLE route_master TO prod_read_user;


--
-- Name: route_master_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE route_master_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE route_master_seq FROM postgres;
GRANT ALL ON SEQUENCE route_master_seq TO postgres;
GRANT ALL ON SEQUENCE route_master_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE route_master_seq TO prod_read_user;


--
-- Name: route_stage_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE route_stage_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE route_stage_seq FROM postgres;
GRANT ALL ON SEQUENCE route_stage_seq TO postgres;
GRANT ALL ON SEQUENCE route_stage_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE route_stage_seq TO prod_read_user;


--
-- Name: rta_employee; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE rta_employee FROM PUBLIC;
REVOKE ALL ON TABLE rta_employee FROM postgres;
GRANT ALL ON TABLE rta_employee TO postgres;
GRANT ALL ON TABLE rta_employee TO rtamigprod;
GRANT SELECT ON TABLE rta_employee TO prod_read_user;


--
-- Name: rta_employee_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE rta_employee_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE rta_employee_seq FROM postgres;
GRANT ALL ON SEQUENCE rta_employee_seq TO postgres;
GRANT ALL ON SEQUENCE rta_employee_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE rta_employee_seq TO prod_read_user;


--
-- Name: rta_financedetails_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE rta_financedetails_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE rta_financedetails_seq FROM postgres;
GRANT ALL ON SEQUENCE rta_financedetails_seq TO postgres;
GRANT ALL ON SEQUENCE rta_financedetails_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE rta_financedetails_seq TO prod_read_user;


--
-- Name: rta_financer_master_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE rta_financer_master_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE rta_financer_master_seq FROM postgres;
GRANT ALL ON SEQUENCE rta_financer_master_seq TO postgres;
GRANT ALL ON SEQUENCE rta_financer_master_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE rta_financer_master_seq TO prod_read_user;


--
-- Name: rta_mandal_mapping; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE rta_mandal_mapping FROM PUBLIC;
REVOKE ALL ON TABLE rta_mandal_mapping FROM postgres;
GRANT ALL ON TABLE rta_mandal_mapping TO postgres;
GRANT ALL ON TABLE rta_mandal_mapping TO rtamigprod;
GRANT SELECT ON TABLE rta_mandal_mapping TO prod_read_user;


--
-- Name: rta_office; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE rta_office FROM PUBLIC;
REVOKE ALL ON TABLE rta_office FROM postgres;
GRANT ALL ON TABLE rta_office TO postgres;
GRANT ALL ON TABLE rta_office TO rtamigprod;
GRANT SELECT ON TABLE rta_office TO prod_read_user;


--
-- Name: rta_office_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE rta_office_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE rta_office_seq FROM postgres;
GRANT ALL ON SEQUENCE rta_office_seq TO postgres;
GRANT ALL ON SEQUENCE rta_office_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE rta_office_seq TO prod_read_user;


--
-- Name: rta_offices_serial_no; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE rta_offices_serial_no FROM PUBLIC;
REVOKE ALL ON TABLE rta_offices_serial_no FROM postgres;
GRANT ALL ON TABLE rta_offices_serial_no TO postgres;
GRANT ALL ON TABLE rta_offices_serial_no TO rtamigprod;
GRANT SELECT ON TABLE rta_offices_serial_no TO prod_read_user;


--
-- Name: rta_offices_serial_no_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE rta_offices_serial_no_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE rta_offices_serial_no_seq FROM postgres;
GRANT ALL ON SEQUENCE rta_offices_serial_no_seq TO postgres;
GRANT ALL ON SEQUENCE rta_offices_serial_no_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE rta_offices_serial_no_seq TO prod_read_user;


--
-- Name: rta_service_charge; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE rta_service_charge FROM PUBLIC;
REVOKE ALL ON TABLE rta_service_charge FROM postgres;
GRANT ALL ON TABLE rta_service_charge TO postgres;
GRANT ALL ON TABLE rta_service_charge TO rtamigprod;
GRANT SELECT ON TABLE rta_service_charge TO prod_read_user;


--
-- Name: rta_service_charge_id_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE rta_service_charge_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE rta_service_charge_id_seq FROM postgres;
GRANT ALL ON SEQUENCE rta_service_charge_id_seq TO postgres;
GRANT ALL ON SEQUENCE rta_service_charge_id_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE rta_service_charge_id_seq TO prod_read_user;


--
-- Name: rta_vehicle_tax; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE rta_vehicle_tax FROM PUBLIC;
REVOKE ALL ON TABLE rta_vehicle_tax FROM postgres;
GRANT ALL ON TABLE rta_vehicle_tax TO postgres;
GRANT ALL ON TABLE rta_vehicle_tax TO rtamigprod;
GRANT SELECT ON TABLE rta_vehicle_tax TO prod_read_user;


--
-- Name: rta_vehicle_tax_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE rta_vehicle_tax_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE rta_vehicle_tax_seq FROM postgres;
GRANT ALL ON SEQUENCE rta_vehicle_tax_seq TO postgres;
GRANT ALL ON SEQUENCE rta_vehicle_tax_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE rta_vehicle_tax_seq TO prod_read_user;


--
-- Name: sbi_ddo; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE sbi_ddo FROM PUBLIC;
REVOKE ALL ON TABLE sbi_ddo FROM postgres;
GRANT ALL ON TABLE sbi_ddo TO postgres;
GRANT ALL ON TABLE sbi_ddo TO rtamigprod;
GRANT SELECT ON TABLE sbi_ddo TO prod_read_user;


--
-- Name: sbi_ddo_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE sbi_ddo_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE sbi_ddo_seq FROM postgres;
GRANT ALL ON SEQUENCE sbi_ddo_seq TO postgres;
GRANT ALL ON SEQUENCE sbi_ddo_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE sbi_ddo_seq TO prod_read_user;


--
-- Name: sbi_dist_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE sbi_dist_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE sbi_dist_seq FROM postgres;
GRANT ALL ON SEQUENCE sbi_dist_seq TO postgres;
GRANT ALL ON SEQUENCE sbi_dist_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE sbi_dist_seq TO prod_read_user;


--
-- Name: sbi_distribution; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE sbi_distribution FROM PUBLIC;
REVOKE ALL ON TABLE sbi_distribution FROM postgres;
GRANT ALL ON TABLE sbi_distribution TO postgres;
GRANT ALL ON TABLE sbi_distribution TO rtamigprod;
GRANT SELECT ON TABLE sbi_distribution TO prod_read_user;


--
-- Name: second_vehicle_details; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE second_vehicle_details FROM PUBLIC;
REVOKE ALL ON TABLE second_vehicle_details FROM postgres;
GRANT ALL ON TABLE second_vehicle_details TO postgres;
GRANT ALL ON TABLE second_vehicle_details TO rtamigprod;
GRANT SELECT ON TABLE second_vehicle_details TO prod_read_user;


--
-- Name: second_vehicle_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE second_vehicle_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE second_vehicle_seq FROM postgres;
GRANT ALL ON SEQUENCE second_vehicle_seq TO postgres;
GRANT ALL ON SEQUENCE second_vehicle_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE second_vehicle_seq TO prod_read_user;


--
-- Name: special_no_rtaseq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE special_no_rtaseq FROM PUBLIC;
REVOKE ALL ON SEQUENCE special_no_rtaseq FROM postgres;
GRANT ALL ON SEQUENCE special_no_rtaseq TO postgres;
GRANT ALL ON SEQUENCE special_no_rtaseq TO rtamigprod;
GRANT SELECT ON SEQUENCE special_no_rtaseq TO prod_read_user;


--
-- Name: special_no_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE special_no_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE special_no_seq FROM postgres;
GRANT ALL ON SEQUENCE special_no_seq TO postgres;
GRANT ALL ON SEQUENCE special_no_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE special_no_seq TO prod_read_user;


--
-- Name: special_number; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE special_number FROM PUBLIC;
REVOKE ALL ON TABLE special_number FROM postgres;
GRANT ALL ON TABLE special_number TO postgres;
GRANT ALL ON TABLE special_number TO rtamigprod;
GRANT SELECT ON TABLE special_number TO prod_read_user;


--
-- Name: special_number_fee; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE special_number_fee FROM PUBLIC;
REVOKE ALL ON SEQUENCE special_number_fee FROM postgres;
GRANT ALL ON SEQUENCE special_number_fee TO postgres;
GRANT ALL ON SEQUENCE special_number_fee TO rtamigprod;
GRANT SELECT ON SEQUENCE special_number_fee TO prod_read_user;


--
-- Name: special_number_fee_details; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE special_number_fee_details FROM PUBLIC;
REVOKE ALL ON TABLE special_number_fee_details FROM postgres;
GRANT ALL ON TABLE special_number_fee_details TO postgres;
GRANT ALL ON TABLE special_number_fee_details TO rtamigprod;
GRANT SELECT ON TABLE special_number_fee_details TO prod_read_user;


--
-- Name: special_number_rta_office; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE special_number_rta_office FROM PUBLIC;
REVOKE ALL ON TABLE special_number_rta_office FROM postgres;
GRANT ALL ON TABLE special_number_rta_office TO postgres;
GRANT ALL ON TABLE special_number_rta_office TO rtamigprod;
GRANT SELECT ON TABLE special_number_rta_office TO prod_read_user;


--
-- Name: state; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE state FROM PUBLIC;
REVOKE ALL ON TABLE state FROM postgres;
GRANT ALL ON TABLE state TO postgres;
GRANT ALL ON TABLE state TO rtamigprod;
GRANT SELECT ON TABLE state TO prod_read_user;


--
-- Name: state_id_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE state_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE state_id_seq FROM postgres;
GRANT ALL ON SEQUENCE state_id_seq TO postgres;
GRANT ALL ON SEQUENCE state_id_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE state_id_seq TO prod_read_user;


--
-- Name: status_reference; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE status_reference FROM PUBLIC;
REVOKE ALL ON TABLE status_reference FROM postgres;
GRANT ALL ON TABLE status_reference TO postgres;
GRANT ALL ON TABLE status_reference TO rtamigprod;
GRANT SELECT ON TABLE status_reference TO prod_read_user;


--
-- Name: stoppage_application; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE stoppage_application FROM PUBLIC;
REVOKE ALL ON TABLE stoppage_application FROM postgres;
GRANT ALL ON TABLE stoppage_application TO postgres;
GRANT ALL ON TABLE stoppage_application TO rtamigprod;
GRANT SELECT ON TABLE stoppage_application TO prod_read_user;


--
-- Name: stoppage_application_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE stoppage_application_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE stoppage_application_seq FROM postgres;
GRANT ALL ON SEQUENCE stoppage_application_seq TO postgres;
GRANT ALL ON SEQUENCE stoppage_application_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE stoppage_application_seq TO prod_read_user;


--
-- Name: stoppage_fee; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE stoppage_fee FROM PUBLIC;
REVOKE ALL ON TABLE stoppage_fee FROM postgres;
GRANT ALL ON TABLE stoppage_fee TO postgres;
GRANT ALL ON TABLE stoppage_fee TO rtamigprod;
GRANT SELECT ON TABLE stoppage_fee TO prod_read_user;


--
-- Name: stoppage_fee_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE stoppage_fee_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE stoppage_fee_seq FROM postgres;
GRANT ALL ON SEQUENCE stoppage_fee_seq TO postgres;
GRANT ALL ON SEQUENCE stoppage_fee_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE stoppage_fee_seq TO prod_read_user;


--
-- Name: stoppage_inspection; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE stoppage_inspection FROM PUBLIC;
REVOKE ALL ON TABLE stoppage_inspection FROM postgres;
GRANT ALL ON TABLE stoppage_inspection TO postgres;
GRANT ALL ON TABLE stoppage_inspection TO rtamigprod;
GRANT SELECT ON TABLE stoppage_inspection TO prod_read_user;


--
-- Name: stoppage_inspection_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE stoppage_inspection_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE stoppage_inspection_seq FROM postgres;
GRANT ALL ON SEQUENCE stoppage_inspection_seq TO postgres;
GRANT ALL ON SEQUENCE stoppage_inspection_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE stoppage_inspection_seq TO prod_read_user;


--
-- Name: stoppage_revocation; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE stoppage_revocation FROM PUBLIC;
REVOKE ALL ON TABLE stoppage_revocation FROM postgres;
GRANT ALL ON TABLE stoppage_revocation TO postgres;
GRANT ALL ON TABLE stoppage_revocation TO rtamigprod;
GRANT SELECT ON TABLE stoppage_revocation TO prod_read_user;


--
-- Name: stoppage_revocation_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE stoppage_revocation_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE stoppage_revocation_seq FROM postgres;
GRANT ALL ON SEQUENCE stoppage_revocation_seq TO postgres;
GRANT ALL ON SEQUENCE stoppage_revocation_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE stoppage_revocation_seq TO prod_read_user;


--
-- Name: susp_drivers_license_dtls; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE susp_drivers_license_dtls FROM PUBLIC;
REVOKE ALL ON TABLE susp_drivers_license_dtls FROM postgres;
GRANT ALL ON TABLE susp_drivers_license_dtls TO postgres;
GRANT ALL ON TABLE susp_drivers_license_dtls TO rtamigprod;
GRANT SELECT ON TABLE susp_drivers_license_dtls TO prod_read_user;


--
-- Name: suspended_rc_no; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE suspended_rc_no FROM PUBLIC;
REVOKE ALL ON TABLE suspended_rc_no FROM postgres;
GRANT ALL ON TABLE suspended_rc_no TO postgres;
GRANT ALL ON TABLE suspended_rc_no TO rtamigprod;
GRANT SELECT ON TABLE suspended_rc_no TO prod_read_user;


--
-- Name: suspended_rc_no_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE suspended_rc_no_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE suspended_rc_no_seq FROM postgres;
GRANT ALL ON SEQUENCE suspended_rc_no_seq TO postgres;
GRANT ALL ON SEQUENCE suspended_rc_no_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE suspended_rc_no_seq TO prod_read_user;


--
-- Name: tax_detail; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE tax_detail FROM PUBLIC;
REVOKE ALL ON TABLE tax_detail FROM postgres;
GRANT ALL ON TABLE tax_detail TO postgres;
GRANT ALL ON TABLE tax_detail TO rtamigprod;
GRANT SELECT ON TABLE tax_detail TO prod_read_user;


--
-- Name: tax_detail_history; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE tax_detail_history FROM PUBLIC;
REVOKE ALL ON TABLE tax_detail_history FROM postgres;
GRANT ALL ON TABLE tax_detail_history TO postgres;
GRANT ALL ON TABLE tax_detail_history TO rtamigprod;
GRANT SELECT ON TABLE tax_detail_history TO prod_read_user;


--
-- Name: tax_dtl_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE tax_dtl_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE tax_dtl_seq FROM postgres;
GRANT ALL ON SEQUENCE tax_dtl_seq TO postgres;
GRANT ALL ON SEQUENCE tax_dtl_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE tax_dtl_seq TO prod_read_user;


--
-- Name: tax_hist_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE tax_hist_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE tax_hist_seq FROM postgres;
GRANT ALL ON SEQUENCE tax_hist_seq TO postgres;
GRANT ALL ON SEQUENCE tax_hist_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE tax_hist_seq TO prod_read_user;


--
-- Name: tax_master; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE tax_master FROM PUBLIC;
REVOKE ALL ON TABLE tax_master FROM postgres;
GRANT ALL ON TABLE tax_master TO postgres;
GRANT ALL ON TABLE tax_master TO rtamigprod;
GRANT SELECT ON TABLE tax_master TO prod_read_user;


--
-- Name: tax_master_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE tax_master_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE tax_master_seq FROM postgres;
GRANT ALL ON SEQUENCE tax_master_seq TO postgres;
GRANT ALL ON SEQUENCE tax_master_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE tax_master_seq TO prod_read_user;


--
-- Name: tax_type; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE tax_type FROM PUBLIC;
REVOKE ALL ON TABLE tax_type FROM postgres;
GRANT ALL ON TABLE tax_type TO postgres;
GRANT ALL ON TABLE tax_type TO rtamigprod;
GRANT SELECT ON TABLE tax_type TO prod_read_user;


--
-- Name: tax_type_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE tax_type_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE tax_type_seq FROM postgres;
GRANT ALL ON SEQUENCE tax_type_seq TO postgres;
GRANT ALL ON SEQUENCE tax_type_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE tax_type_seq TO prod_read_user;


--
-- Name: temp_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE temp_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE temp_seq FROM postgres;
GRANT ALL ON SEQUENCE temp_seq TO postgres;
GRANT ALL ON SEQUENCE temp_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE temp_seq TO prod_read_user;


--
-- Name: tmp_new_vrc_mig; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE tmp_new_vrc_mig FROM PUBLIC;
REVOKE ALL ON TABLE tmp_new_vrc_mig FROM postgres;
GRANT ALL ON TABLE tmp_new_vrc_mig TO postgres;
GRANT ALL ON TABLE tmp_new_vrc_mig TO rtamigprod;
GRANT SELECT ON TABLE tmp_new_vrc_mig TO prod_read_user;


--
-- Name: tmp_new_vrc_mig_trpr; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE tmp_new_vrc_mig_trpr FROM PUBLIC;
REVOKE ALL ON TABLE tmp_new_vrc_mig_trpr FROM postgres;
GRANT ALL ON TABLE tmp_new_vrc_mig_trpr TO postgres;
GRANT ALL ON TABLE tmp_new_vrc_mig_trpr TO rtamigprod;
GRANT SELECT ON TABLE tmp_new_vrc_mig_trpr TO prod_read_user;


--
-- Name: tr_series_master; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE tr_series_master FROM PUBLIC;
REVOKE ALL ON TABLE tr_series_master FROM postgres;
GRANT ALL ON TABLE tr_series_master TO postgres;
GRANT ALL ON TABLE tr_series_master TO rtamigprod;
GRANT SELECT ON TABLE tr_series_master TO prod_read_user;


--
-- Name: tr_series_master_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE tr_series_master_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE tr_series_master_seq FROM postgres;
GRANT ALL ON SEQUENCE tr_series_master_seq TO postgres;
GRANT ALL ON SEQUENCE tr_series_master_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE tr_series_master_seq TO prod_read_user;


--
-- Name: tran_dtl_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE tran_dtl_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE tran_dtl_seq FROM postgres;
GRANT ALL ON SEQUENCE tran_dtl_seq TO postgres;
GRANT ALL ON SEQUENCE tran_dtl_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE tran_dtl_seq TO prod_read_user;


--
-- Name: trans_hist_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE trans_hist_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE trans_hist_seq FROM postgres;
GRANT ALL ON SEQUENCE trans_hist_seq TO postgres;
GRANT ALL ON SEQUENCE trans_hist_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE trans_hist_seq TO prod_read_user;


--
-- Name: transaction_detail; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE transaction_detail FROM PUBLIC;
REVOKE ALL ON TABLE transaction_detail FROM postgres;
GRANT ALL ON TABLE transaction_detail TO postgres;
GRANT ALL ON TABLE transaction_detail TO rtamigprod;
GRANT SELECT ON TABLE transaction_detail TO prod_read_user;


--
-- Name: transaction_history; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE transaction_history FROM PUBLIC;
REVOKE ALL ON TABLE transaction_history FROM postgres;
GRANT ALL ON TABLE transaction_history TO postgres;
GRANT ALL ON TABLE transaction_history TO rtamigprod;
GRANT SELECT ON TABLE transaction_history TO prod_read_user;


--
-- Name: user_attachment_details; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE user_attachment_details FROM PUBLIC;
REVOKE ALL ON TABLE user_attachment_details FROM postgres;
GRANT ALL ON TABLE user_attachment_details TO postgres;
GRANT ALL ON TABLE user_attachment_details TO rtamigprod;
GRANT SELECT ON TABLE user_attachment_details TO prod_read_user;


--
-- Name: user_attachment_details_sequence; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE user_attachment_details_sequence FROM PUBLIC;
REVOKE ALL ON SEQUENCE user_attachment_details_sequence FROM postgres;
GRANT ALL ON SEQUENCE user_attachment_details_sequence TO postgres;
GRANT ALL ON SEQUENCE user_attachment_details_sequence TO rtamigprod;
GRANT SELECT ON SEQUENCE user_attachment_details_sequence TO prod_read_user;


--
-- Name: user_history_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE user_history_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE user_history_seq FROM postgres;
GRANT ALL ON SEQUENCE user_history_seq TO postgres;
GRANT ALL ON SEQUENCE user_history_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE user_history_seq TO prod_read_user;


--
-- Name: user_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE user_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE user_seq FROM postgres;
GRANT ALL ON SEQUENCE user_seq TO postgres;
GRANT ALL ON SEQUENCE user_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE user_seq TO prod_read_user;


--
-- Name: user_transfer_history; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE user_transfer_history FROM PUBLIC;
REVOKE ALL ON TABLE user_transfer_history FROM postgres;
GRANT ALL ON TABLE user_transfer_history TO postgres;
GRANT ALL ON TABLE user_transfer_history TO rtamigprod;
GRANT SELECT ON TABLE user_transfer_history TO prod_read_user;


--
-- Name: user_transfer_sequence; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE user_transfer_sequence FROM PUBLIC;
REVOKE ALL ON SEQUENCE user_transfer_sequence FROM postgres;
GRANT ALL ON SEQUENCE user_transfer_sequence TO postgres;
GRANT ALL ON SEQUENCE user_transfer_sequence TO rtamigprod;
GRANT SELECT ON SEQUENCE user_transfer_sequence TO prod_read_user;


--
-- Name: users; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE users FROM PUBLIC;
REVOKE ALL ON TABLE users FROM postgres;
GRANT ALL ON TABLE users TO postgres;
GRANT ALL ON TABLE users TO rtamigprod;
GRANT SELECT ON TABLE users TO prod_read_user;


--
-- Name: vahan_master; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vahan_master FROM PUBLIC;
REVOKE ALL ON TABLE vahan_master FROM postgres;
GRANT ALL ON TABLE vahan_master TO postgres;
GRANT ALL ON TABLE vahan_master TO rtamigprod;
GRANT SELECT ON TABLE vahan_master TO prod_read_user;


--
-- Name: vcr_details; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vcr_details FROM PUBLIC;
REVOKE ALL ON TABLE vcr_details FROM postgres;
GRANT ALL ON TABLE vcr_details TO postgres;
GRANT ALL ON TABLE vcr_details TO rtamigprod;
GRANT SELECT ON TABLE vcr_details TO prod_read_user;


--
-- Name: vcr_details_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE vcr_details_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE vcr_details_seq FROM postgres;
GRANT ALL ON SEQUENCE vcr_details_seq TO postgres;
GRANT ALL ON SEQUENCE vcr_details_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE vcr_details_seq TO prod_read_user;


--
-- Name: vcr_offense_logs; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vcr_offense_logs FROM PUBLIC;
REVOKE ALL ON TABLE vcr_offense_logs FROM postgres;
GRANT ALL ON TABLE vcr_offense_logs TO postgres;
GRANT ALL ON TABLE vcr_offense_logs TO rtamigprod;
GRANT SELECT ON TABLE vcr_offense_logs TO prod_read_user;


--
-- Name: vcr_offense_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE vcr_offense_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE vcr_offense_seq FROM postgres;
GRANT ALL ON SEQUENCE vcr_offense_seq TO postgres;
GRANT ALL ON SEQUENCE vcr_offense_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE vcr_offense_seq TO prod_read_user;


--
-- Name: vcr_payment_logs; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vcr_payment_logs FROM PUBLIC;
REVOKE ALL ON TABLE vcr_payment_logs FROM postgres;
GRANT ALL ON TABLE vcr_payment_logs TO postgres;
GRANT ALL ON TABLE vcr_payment_logs TO rtamigprod;
GRANT SELECT ON TABLE vcr_payment_logs TO prod_read_user;


--
-- Name: vcr_tran_dtl_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE vcr_tran_dtl_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE vcr_tran_dtl_seq FROM postgres;
GRANT ALL ON SEQUENCE vcr_tran_dtl_seq TO postgres;
GRANT ALL ON SEQUENCE vcr_tran_dtl_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE vcr_tran_dtl_seq TO prod_read_user;


--
-- Name: vcr_transaction_details; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vcr_transaction_details FROM PUBLIC;
REVOKE ALL ON TABLE vcr_transaction_details FROM postgres;
GRANT ALL ON TABLE vcr_transaction_details TO postgres;
GRANT ALL ON TABLE vcr_transaction_details TO rtamigprod;
GRANT SELECT ON TABLE vcr_transaction_details TO prod_read_user;


--
-- Name: vcrpayment_log_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE vcrpayment_log_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE vcrpayment_log_seq FROM postgres;
GRANT ALL ON SEQUENCE vcrpayment_log_seq TO postgres;
GRANT ALL ON SEQUENCE vcrpayment_log_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE vcrpayment_log_seq TO prod_read_user;


--
-- Name: vehicle_alter_attachment_sequence; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE vehicle_alter_attachment_sequence FROM PUBLIC;
REVOKE ALL ON SEQUENCE vehicle_alter_attachment_sequence FROM postgres;
GRANT ALL ON SEQUENCE vehicle_alter_attachment_sequence TO postgres;
GRANT ALL ON SEQUENCE vehicle_alter_attachment_sequence TO rtamigprod;
GRANT SELECT ON SEQUENCE vehicle_alter_attachment_sequence TO prod_read_user;


--
-- Name: vehicle_alteration_attachment; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vehicle_alteration_attachment FROM PUBLIC;
REVOKE ALL ON TABLE vehicle_alteration_attachment FROM postgres;
GRANT ALL ON TABLE vehicle_alteration_attachment TO postgres;
GRANT ALL ON TABLE vehicle_alteration_attachment TO rtamigprod;
GRANT SELECT ON TABLE vehicle_alteration_attachment TO prod_read_user;


--
-- Name: vehicle_alteration_details; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vehicle_alteration_details FROM PUBLIC;
REVOKE ALL ON TABLE vehicle_alteration_details FROM postgres;
GRANT ALL ON TABLE vehicle_alteration_details TO postgres;
GRANT ALL ON TABLE vehicle_alteration_details TO rtamigprod;
GRANT SELECT ON TABLE vehicle_alteration_details TO prod_read_user;


--
-- Name: vehicle_alteration_details_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE vehicle_alteration_details_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE vehicle_alteration_details_seq FROM postgres;
GRANT ALL ON SEQUENCE vehicle_alteration_details_seq TO postgres;
GRANT ALL ON SEQUENCE vehicle_alteration_details_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE vehicle_alteration_details_seq TO prod_read_user;


--
-- Name: vehicle_bharat_stage; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vehicle_bharat_stage FROM PUBLIC;
REVOKE ALL ON TABLE vehicle_bharat_stage FROM postgres;
GRANT ALL ON TABLE vehicle_bharat_stage TO postgres;
GRANT ALL ON TABLE vehicle_bharat_stage TO rtamigprod;
GRANT SELECT ON TABLE vehicle_bharat_stage TO prod_read_user;


--
-- Name: vehicle_bharat_stage_backup; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vehicle_bharat_stage_backup FROM PUBLIC;
REVOKE ALL ON TABLE vehicle_bharat_stage_backup FROM postgres;
GRANT ALL ON TABLE vehicle_bharat_stage_backup TO postgres;
GRANT ALL ON TABLE vehicle_bharat_stage_backup TO rtamigprod;
GRANT SELECT ON TABLE vehicle_bharat_stage_backup TO prod_read_user;


--
-- Name: vehicle_bharat_stage_duplicates; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vehicle_bharat_stage_duplicates FROM PUBLIC;
REVOKE ALL ON TABLE vehicle_bharat_stage_duplicates FROM postgres;
GRANT ALL ON TABLE vehicle_bharat_stage_duplicates TO postgres;
GRANT ALL ON TABLE vehicle_bharat_stage_duplicates TO rtamigprod;
GRANT SELECT ON TABLE vehicle_bharat_stage_duplicates TO prod_read_user;


--
-- Name: vehicle_billing_details; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vehicle_billing_details FROM PUBLIC;
REVOKE ALL ON TABLE vehicle_billing_details FROM postgres;
GRANT ALL ON TABLE vehicle_billing_details TO postgres;
GRANT ALL ON TABLE vehicle_billing_details TO rtamigprod;
GRANT SELECT ON TABLE vehicle_billing_details TO prod_read_user;


--
-- Name: vehicle_billing_details_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE vehicle_billing_details_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE vehicle_billing_details_seq FROM postgres;
GRANT ALL ON SEQUENCE vehicle_billing_details_seq TO postgres;
GRANT ALL ON SEQUENCE vehicle_billing_details_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE vehicle_billing_details_seq TO prod_read_user;


--
-- Name: vehicle_change_history_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE vehicle_change_history_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE vehicle_change_history_seq FROM postgres;
GRANT ALL ON SEQUENCE vehicle_change_history_seq TO postgres;
GRANT ALL ON SEQUENCE vehicle_change_history_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE vehicle_change_history_seq TO prod_read_user;


--
-- Name: vehicle_class; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vehicle_class FROM PUBLIC;
REVOKE ALL ON TABLE vehicle_class FROM postgres;
GRANT ALL ON TABLE vehicle_class TO postgres;
GRANT ALL ON TABLE vehicle_class TO rtamigprod;
GRANT SELECT ON TABLE vehicle_class TO prod_read_user;


--
-- Name: vehicle_class_bck; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vehicle_class_bck FROM PUBLIC;
REVOKE ALL ON TABLE vehicle_class_bck FROM postgres;
GRANT ALL ON TABLE vehicle_class_bck TO postgres;
GRANT ALL ON TABLE vehicle_class_bck TO rtamigprod;
GRANT SELECT ON TABLE vehicle_class_bck TO prod_read_user;


--
-- Name: vehicle_class_desc; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vehicle_class_desc FROM PUBLIC;
REVOKE ALL ON TABLE vehicle_class_desc FROM postgres;
GRANT ALL ON TABLE vehicle_class_desc TO postgres;
GRANT ALL ON TABLE vehicle_class_desc TO rtamigprod;
GRANT SELECT ON TABLE vehicle_class_desc TO prod_read_user;


--
-- Name: vehicle_class_desc_bck; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vehicle_class_desc_bck FROM PUBLIC;
REVOKE ALL ON TABLE vehicle_class_desc_bck FROM postgres;
GRANT ALL ON TABLE vehicle_class_desc_bck TO postgres;
GRANT ALL ON TABLE vehicle_class_desc_bck TO rtamigprod;
GRANT SELECT ON TABLE vehicle_class_desc_bck TO prod_read_user;


--
-- Name: vehicle_class_desc_id_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE vehicle_class_desc_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE vehicle_class_desc_id_seq FROM postgres;
GRANT ALL ON SEQUENCE vehicle_class_desc_id_seq TO postgres;
GRANT ALL ON SEQUENCE vehicle_class_desc_id_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE vehicle_class_desc_id_seq TO prod_read_user;


--
-- Name: vehicle_class_id_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE vehicle_class_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE vehicle_class_id_seq FROM postgres;
GRANT ALL ON SEQUENCE vehicle_class_id_seq TO postgres;
GRANT ALL ON SEQUENCE vehicle_class_id_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE vehicle_class_id_seq TO prod_read_user;


--
-- Name: vehicle_current_tax_details; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vehicle_current_tax_details FROM PUBLIC;
REVOKE ALL ON TABLE vehicle_current_tax_details FROM postgres;
GRANT ALL ON TABLE vehicle_current_tax_details TO postgres;
GRANT ALL ON TABLE vehicle_current_tax_details TO rtamigprod;
GRANT SELECT ON TABLE vehicle_current_tax_details TO prod_read_user;


--
-- Name: vehicle_current_tax_details_dup; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vehicle_current_tax_details_dup FROM PUBLIC;
REVOKE ALL ON TABLE vehicle_current_tax_details_dup FROM postgres;
GRANT ALL ON TABLE vehicle_current_tax_details_dup TO postgres;
GRANT ALL ON TABLE vehicle_current_tax_details_dup TO rtamigprod;
GRANT SELECT ON TABLE vehicle_current_tax_details_dup TO prod_read_user;


--
-- Name: vehicle_current_tax_details_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE vehicle_current_tax_details_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE vehicle_current_tax_details_seq FROM postgres;
GRANT ALL ON SEQUENCE vehicle_current_tax_details_seq TO postgres;
GRANT ALL ON SEQUENCE vehicle_current_tax_details_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE vehicle_current_tax_details_seq TO prod_read_user;


--
-- Name: vehicle_details; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vehicle_details FROM PUBLIC;
REVOKE ALL ON TABLE vehicle_details FROM postgres;
GRANT ALL ON TABLE vehicle_details TO postgres;
GRANT ALL ON TABLE vehicle_details TO rtamigprod;
GRANT SELECT ON TABLE vehicle_details TO prod_read_user;


--
-- Name: vehicle_details_dup; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vehicle_details_dup FROM PUBLIC;
REVOKE ALL ON TABLE vehicle_details_dup FROM postgres;
GRANT ALL ON TABLE vehicle_details_dup TO postgres;
GRANT ALL ON TABLE vehicle_details_dup TO rtamigprod;
GRANT SELECT ON TABLE vehicle_details_dup TO prod_read_user;


--
-- Name: vehicle_details_history; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vehicle_details_history FROM PUBLIC;
REVOKE ALL ON TABLE vehicle_details_history FROM postgres;
GRANT ALL ON TABLE vehicle_details_history TO postgres;
GRANT ALL ON TABLE vehicle_details_history TO rtamigprod;
GRANT SELECT ON TABLE vehicle_details_history TO prod_read_user;


--
-- Name: vehicle_dtl_history_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE vehicle_dtl_history_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE vehicle_dtl_history_seq FROM postgres;
GRANT ALL ON SEQUENCE vehicle_dtl_history_seq TO postgres;
GRANT ALL ON SEQUENCE vehicle_dtl_history_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE vehicle_dtl_history_seq TO prod_read_user;


--
-- Name: vehicle_dtl_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE vehicle_dtl_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE vehicle_dtl_seq FROM postgres;
GRANT ALL ON SEQUENCE vehicle_dtl_seq TO postgres;
GRANT ALL ON SEQUENCE vehicle_dtl_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE vehicle_dtl_seq TO prod_read_user;


--
-- Name: vehicle_duplicate_registration; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vehicle_duplicate_registration FROM PUBLIC;
REVOKE ALL ON TABLE vehicle_duplicate_registration FROM postgres;
GRANT ALL ON TABLE vehicle_duplicate_registration TO postgres;
GRANT ALL ON TABLE vehicle_duplicate_registration TO rtamigprod;
GRANT SELECT ON TABLE vehicle_duplicate_registration TO prod_read_user;


--
-- Name: vehicle_duplicate_registration_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE vehicle_duplicate_registration_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE vehicle_duplicate_registration_seq FROM postgres;
GRANT ALL ON SEQUENCE vehicle_duplicate_registration_seq TO postgres;
GRANT ALL ON SEQUENCE vehicle_duplicate_registration_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE vehicle_duplicate_registration_seq TO prod_read_user;


--
-- Name: vehicle_finance_details; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vehicle_finance_details FROM PUBLIC;
REVOKE ALL ON TABLE vehicle_finance_details FROM postgres;
GRANT ALL ON TABLE vehicle_finance_details TO postgres;
GRANT ALL ON TABLE vehicle_finance_details TO rtamigprod;
GRANT SELECT ON TABLE vehicle_finance_details TO prod_read_user;


--
-- Name: vehicle_noc; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vehicle_noc FROM PUBLIC;
REVOKE ALL ON TABLE vehicle_noc FROM postgres;
GRANT ALL ON TABLE vehicle_noc TO postgres;
GRANT ALL ON TABLE vehicle_noc TO rtamigprod;
GRANT SELECT ON TABLE vehicle_noc TO prod_read_user;


--
-- Name: vehicle_noc_address; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vehicle_noc_address FROM PUBLIC;
REVOKE ALL ON TABLE vehicle_noc_address FROM postgres;
GRANT ALL ON TABLE vehicle_noc_address TO postgres;
GRANT ALL ON TABLE vehicle_noc_address TO rtamigprod;
GRANT SELECT ON TABLE vehicle_noc_address TO prod_read_user;


--
-- Name: vehicle_noc_history; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vehicle_noc_history FROM PUBLIC;
REVOKE ALL ON TABLE vehicle_noc_history FROM postgres;
GRANT ALL ON TABLE vehicle_noc_history TO postgres;
GRANT ALL ON TABLE vehicle_noc_history TO rtamigprod;
GRANT SELECT ON TABLE vehicle_noc_history TO prod_read_user;


--
-- Name: vehicle_pr_release; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vehicle_pr_release FROM PUBLIC;
REVOKE ALL ON TABLE vehicle_pr_release FROM postgres;
GRANT ALL ON TABLE vehicle_pr_release TO postgres;
GRANT ALL ON TABLE vehicle_pr_release TO rtamigprod;
GRANT SELECT ON TABLE vehicle_pr_release TO prod_read_user;


--
-- Name: vehicle_pr_release_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE vehicle_pr_release_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE vehicle_pr_release_seq FROM postgres;
GRANT ALL ON SEQUENCE vehicle_pr_release_seq TO postgres;
GRANT ALL ON SEQUENCE vehicle_pr_release_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE vehicle_pr_release_seq TO prod_read_user;


--
-- Name: vehicle_puc; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vehicle_puc FROM PUBLIC;
REVOKE ALL ON TABLE vehicle_puc FROM postgres;
GRANT ALL ON TABLE vehicle_puc TO postgres;
GRANT ALL ON TABLE vehicle_puc TO rtamigprod;
GRANT SELECT ON TABLE vehicle_puc TO prod_read_user;


--
-- Name: vehicle_puc_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE vehicle_puc_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE vehicle_puc_seq FROM postgres;
GRANT ALL ON SEQUENCE vehicle_puc_seq TO postgres;
GRANT ALL ON SEQUENCE vehicle_puc_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE vehicle_puc_seq TO prod_read_user;


--
-- Name: vehicle_quarterly; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vehicle_quarterly FROM PUBLIC;
REVOKE ALL ON TABLE vehicle_quarterly FROM postgres;
GRANT ALL ON TABLE vehicle_quarterly TO postgres;
GRANT ALL ON TABLE vehicle_quarterly TO rtamigprod;
GRANT SELECT ON TABLE vehicle_quarterly TO prod_read_user;


--
-- Name: vehicle_quarterly_bck; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vehicle_quarterly_bck FROM PUBLIC;
REVOKE ALL ON TABLE vehicle_quarterly_bck FROM postgres;
GRANT ALL ON TABLE vehicle_quarterly_bck TO postgres;
GRANT ALL ON TABLE vehicle_quarterly_bck TO rtamigprod;
GRANT SELECT ON TABLE vehicle_quarterly_bck TO prod_read_user;


--
-- Name: vehicle_quarterly_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE vehicle_quarterly_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE vehicle_quarterly_seq FROM postgres;
GRANT ALL ON SEQUENCE vehicle_quarterly_seq TO postgres;
GRANT ALL ON SEQUENCE vehicle_quarterly_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE vehicle_quarterly_seq TO prod_read_user;


--
-- Name: vehicle_rc; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vehicle_rc FROM PUBLIC;
REVOKE ALL ON TABLE vehicle_rc FROM postgres;
GRANT ALL ON TABLE vehicle_rc TO postgres;
GRANT ALL ON TABLE vehicle_rc TO rtamigprod;
GRANT SELECT ON TABLE vehicle_rc TO prod_read_user;


--
-- Name: vehicle_rc_change_history; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vehicle_rc_change_history FROM PUBLIC;
REVOKE ALL ON TABLE vehicle_rc_change_history FROM postgres;
GRANT ALL ON TABLE vehicle_rc_change_history TO postgres;
GRANT ALL ON TABLE vehicle_rc_change_history TO rtamigprod;
GRANT SELECT ON TABLE vehicle_rc_change_history TO prod_read_user;


--
-- Name: vehicle_rc_dup; Type: ACL; Schema: public; Owner: rtamigprod
--

REVOKE ALL ON TABLE vehicle_rc_dup FROM PUBLIC;
REVOKE ALL ON TABLE vehicle_rc_dup FROM rtamigprod;
GRANT ALL ON TABLE vehicle_rc_dup TO rtamigprod;
GRANT SELECT ON TABLE vehicle_rc_dup TO prod_read_user;


--
-- Name: vehicle_rc_history; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vehicle_rc_history FROM PUBLIC;
REVOKE ALL ON TABLE vehicle_rc_history FROM postgres;
GRANT ALL ON TABLE vehicle_rc_history TO postgres;
GRANT ALL ON TABLE vehicle_rc_history TO rtamigprod;
GRANT SELECT ON TABLE vehicle_rc_history TO prod_read_user;


--
-- Name: vehicle_rc_history_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE vehicle_rc_history_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE vehicle_rc_history_seq FROM postgres;
GRANT ALL ON SEQUENCE vehicle_rc_history_seq TO postgres;
GRANT ALL ON SEQUENCE vehicle_rc_history_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE vehicle_rc_history_seq TO prod_read_user;


--
-- Name: vehicle_reg_fees_master; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vehicle_reg_fees_master FROM PUBLIC;
REVOKE ALL ON TABLE vehicle_reg_fees_master FROM postgres;
GRANT ALL ON TABLE vehicle_reg_fees_master TO postgres;
GRANT ALL ON TABLE vehicle_reg_fees_master TO rtamigprod;
GRANT SELECT ON TABLE vehicle_reg_fees_master TO prod_read_user;


--
-- Name: vehicle_reg_fees_master_id_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE vehicle_reg_fees_master_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE vehicle_reg_fees_master_id_seq FROM postgres;
GRANT ALL ON SEQUENCE vehicle_reg_fees_master_id_seq TO postgres;
GRANT ALL ON SEQUENCE vehicle_reg_fees_master_id_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE vehicle_reg_fees_master_id_seq TO prod_read_user;


--
-- Name: vehicle_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE vehicle_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE vehicle_seq FROM postgres;
GRANT ALL ON SEQUENCE vehicle_seq TO postgres;
GRANT ALL ON SEQUENCE vehicle_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE vehicle_seq TO prod_read_user;


--
-- Name: vehicle_theft; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vehicle_theft FROM PUBLIC;
REVOKE ALL ON TABLE vehicle_theft FROM postgres;
GRANT ALL ON TABLE vehicle_theft TO postgres;
GRANT ALL ON TABLE vehicle_theft TO rtamigprod;
GRANT SELECT ON TABLE vehicle_theft TO prod_read_user;


--
-- Name: vehicle_theft_sequence; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE vehicle_theft_sequence FROM PUBLIC;
REVOKE ALL ON SEQUENCE vehicle_theft_sequence FROM postgres;
GRANT ALL ON SEQUENCE vehicle_theft_sequence TO postgres;
GRANT ALL ON SEQUENCE vehicle_theft_sequence TO rtamigprod;
GRANT SELECT ON SEQUENCE vehicle_theft_sequence TO prod_read_user;


--
-- Name: vehicle_training_period; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vehicle_training_period FROM PUBLIC;
REVOKE ALL ON TABLE vehicle_training_period FROM postgres;
GRANT ALL ON TABLE vehicle_training_period TO postgres;
GRANT ALL ON TABLE vehicle_training_period TO rtamigprod;
GRANT SELECT ON TABLE vehicle_training_period TO prod_read_user;


--
-- Name: vehicle_weight_master; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vehicle_weight_master FROM PUBLIC;
REVOKE ALL ON TABLE vehicle_weight_master FROM postgres;
GRANT ALL ON TABLE vehicle_weight_master TO postgres;
GRANT ALL ON TABLE vehicle_weight_master TO rtamigprod;
GRANT SELECT ON TABLE vehicle_weight_master TO prod_read_user;


--
-- Name: vehicle_weight_master_id_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE vehicle_weight_master_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE vehicle_weight_master_id_seq FROM postgres;
GRANT ALL ON SEQUENCE vehicle_weight_master_id_seq TO postgres;
GRANT ALL ON SEQUENCE vehicle_weight_master_id_seq TO rtamigprod;
GRANT SELECT ON SEQUENCE vehicle_weight_master_id_seq TO prod_read_user;


--
-- PostgreSQL database dump complete
--

