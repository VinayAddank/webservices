package org.rta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.rta.aadhar.api.AadhaTCSService;
import org.rta.aadhar.api.AadharService;
import org.rta.aadhar.api.AadharServiceImpl;
import org.rta.aadhar.api.AadharTCSServiceImpl;
import org.rta.core.helper.applicant.AadhaarLogHelper;
import org.rta.core.helper.vehicle.UnregisteredVehicleModelToVehicleDetailsEntity;
import org.rta.core.helper.vehicle.UnregisteredVehicleToVahanEntityConverter;
import org.rta.core.helper.vehicle.VehicleDetailsEntityToUnregisteredVehicleConverter;
import org.rta.coverfox.service.CoverfoxAPIService;
import org.rta.coverfox.service.impl.CoverfoxAPIServiceImpl;
import org.rta.registration.scheduler.RTAScheduler;
import org.rta.registration.service.vahan.helper.UnregisteredChassisInfoToUnregisteredVehicleModelConverter;
import org.rta.secondvehicle.api.ChassisEngineCheckService;
import org.rta.secondvehicle.api.ChassisEngineCheckServiceImpl;
import org.rta.secondvehicle.api.SecondVehicleService;
import org.rta.secondvehicle.api.SecondVehicleServiceImpl;
import org.rta.secondvehicle.api.VehicleDetailsSyncService;
import org.rta.secondvehicle.api.VehicleDetailsSyncServiceImpl;
import org.rta.vahan.api.registered.VahanClient;
import org.rta.vahan.api.registered.VahanClientImpl;
import org.rta.vahan.api.unregistered.UnregisteredVahanClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class RegistrationApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
	    
	    //System.out.println("https:\\/\\/rta.financier.dev\\/sanctionedletters\\/12-11.pdf".replaceAll("\\\\/", "/"));
		SpringApplication.run(RegistrationApplication.class, args);
	}

	@Bean(name = "sessionFactory")
	public SessionFactory sessionFactory(HibernateEntityManagerFactory factory) {
		return factory.getSessionFactory();
	}

	@Bean
	public UnregisteredVahanClientImpl getVahanClient(@Value("${unreg.vahan.api.userid}") String userId,
			@Value("${unreg.vahan.api.securitykey}") String securityKey) {
		return new UnregisteredVahanClientImpl(userId, securityKey);
	}

	@Bean
	public VahanClient getVehicleService(@Value("${vahan.api.secretkey}") String secretKey) {
	    VahanClient v = new VahanClientImpl(secretKey);
//	    v.getDetails("AP001", "DL4SBG8823");
//	    v.getDetails("AP001", "HR55X4348");
//	    v.getChasisDetails("AP001", "MA3EWDE1S00A59984");
		return v;
	}

	@Bean
	public AadharService getAadharService() {
		return new AadharServiceImpl();
	}

	@Bean
	public AadhaTCSService getAadharTCSService() {
		return new AadharTCSServiceImpl();
	}

	@Bean
	public AadhaarLogHelper getaadhaarLogHelper() {
		return new AadhaarLogHelper();
	}

	@Bean
	public SecondVehicleService getSecondVehicleService() {
		return new SecondVehicleServiceImpl();
	}
	
	@Bean
    public ChassisEngineCheckService checkChessisEngineNo() {
        return new ChassisEngineCheckServiceImpl();
    }

	@Bean
	public SmsEmailService getSmsEmailService() {
		return new SmsEmailServiceImpl();
	}

	@Bean
	public RTAScheduler getRTAScheduler() {
		return new RTAScheduler();
	}

	@SuppressWarnings("rawtypes")
	@Bean(name = "conversionService")
	public ConversionService getConversionService() {
		ConversionServiceFactoryBean csfb = new ConversionServiceFactoryBean();
		Set<Converter> converters = new HashSet<>();
		converters.add(new UnregisteredChassisInfoToUnregisteredVehicleModelConverter());
		converters.add(new UnregisteredVehicleToVahanEntityConverter());
		converters.add(new VehicleDetailsEntityToUnregisteredVehicleConverter());
		converters.add(new UnregisteredVehicleModelToVehicleDetailsEntity());
		csfb.setConverters(converters);
		csfb.afterPropertiesSet();
		return csfb.getObject();
	}

	@Bean
	public WebMvcConfigurerAdapter webMvcConfigurerAdapter() {
		WebMvcConfigurerAdapter webMvcConfigurerAdapter = new WebMvcConfigurerAdapter() {
			@Override
			public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
				configurer.favorPathExtension(true).favorParameter(true).ignoreAcceptHeader(true).useJaf(false)
						.defaultContentType(MediaType.APPLICATION_JSON).mediaType("xml", MediaType.APPLICATION_XML)
						.mediaType("json", MediaType.APPLICATION_JSON);
			}
		};
		return webMvcConfigurerAdapter;
	}

	@Bean
	public MessageConfig messageConfig(@Value("${rta.sms.url}") String url,
			@Value("${rta.sms.accptType}") String accptType, @Value("${rta.sms.X-Version}") String xVersion,
			@Value("${rta.sms.Authorization}") String authorization,
			@Value("${rta.sms.contentType}") String contentType, @Value("${rta.sms.userNm}") String userNm,
			@Value("${rta.sms.password}") String password, @Value("${rta.sms.senderId}") String senderId,
			@Value("${rta.mail.smtp}") String smtp, @Value("${rta.mail.smtpStatus}") String smtpStatus,
			@Value("${rta.mail.host}") String host, @Value("${rta.mail.port}") Integer port,
			@Value("${rta.mail.protocol}") String protocol, @Value("${rta.mail.mailUserName}") String mailUserName,
			@Value("${rta.mail.mailPassword}") String mailPassword,
			@Value("${rta.mail.mailContentType}") String mailContentType) {
		MessageConfig smsEmailConfigs = new MessageConfig();
		smsEmailConfigs.setAcceptPopertyTyp(accptType);
		smsEmailConfigs.setAuthorization(authorization);
		smsEmailConfigs.setMessUrl(url);
		smsEmailConfigs.setUserNm(userNm);
		smsEmailConfigs.setSenderId(senderId);
		smsEmailConfigs.setPasswrd(password);
		smsEmailConfigs.setContentType(contentType);
		smsEmailConfigs.setHost(host);
		smsEmailConfigs.setMailContentType(mailContentType);
		smsEmailConfigs.setMailPassword(mailPassword);
		smsEmailConfigs.setMailUserName(mailUserName);
		smsEmailConfigs.setPort(port);
		smsEmailConfigs.setProtocol(protocol);
		smsEmailConfigs.setSmtp(smtpStatus);
		smsEmailConfigs.setSmtpStatus(smtpStatus);
		return smsEmailConfigs;
	}

	@Bean
	public CoverfoxAPIService getCoverfoxAPIService() {
		return new CoverfoxAPIServiceImpl();
	}

    @Bean
    public VehicleDetailsSyncService getVehicleDetailsSyncService() {
        return new VehicleDetailsSyncServiceImpl();
    }
    
    /*//--- danger code -------------
    @Bean
    public ObjectMapper getJacksonObjectMapper(){
    	 return new ObjectMapper();
    }*/
    
    @Bean
    public RestTemplate restTemplate() {
    	RestTemplate restTemplate = new RestTemplate();
		// Update the Jackson message converter to support text/html
		List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
		for (HttpMessageConverter<?> converter : converters) {
			if (converter instanceof MappingJackson2HttpMessageConverter) {
				try {
					List<MediaType> mediaTypes = new ArrayList<>();
					mediaTypes.add(
							new MediaType("application", "json", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET));
					mediaTypes.add(new MediaType("text", "html", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET));

					((MappingJackson2HttpMessageConverter) converter)
							.setSupportedMediaTypes(Collections.unmodifiableList(mediaTypes));
				} catch (Exception exp) {
					//log.error("Exception occured while updating Jackson mediatype", exp);
				}
			}
		}

		return restTemplate;
    }
    
}

