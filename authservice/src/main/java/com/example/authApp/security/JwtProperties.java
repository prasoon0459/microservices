package com.example.authApp.security;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
//import java.util.Base64;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

	private static PrivateKey secretPrivateKey;
	private static PublicKey secretPublicKey;
	private long validityInMs = 3600000;
	private static String issuer="lite"; 
	//private long validityInMs = 30000;
	private long refreshValidityInMs = 5184000000L;
	private static String refreshSecret;
	
	public JwtProperties() {
		setSecretPrivateKey();
		setSecretPublicKey();
		setRefreshSecret();
	}
	
	private static void setSecretPrivateKey() {
		try {
    		ClassPathResource cpr=new ClassPathResource("private_key.der");
    		byte [] keyBytes=FileCopyUtils.copyToByteArray(cpr.getInputStream());
//			File pvtkFiles=new File(JwtProperties.class.getClassLoader().getResource("private_key.der").getPath());
//			byte[] keyBytes = Files.readAllBytes(pvtkFiles.toPath());
			PKCS8EncodedKeySpec spec =
			  new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory kf = KeyFactory.getInstance("RSA");
			//System.out.println("PrivateKey Format "+kf.generatePrivate(spec).getFormat());
			secretPrivateKey= kf.generatePrivate(spec);
			//System.out.println(Base64.getEncoder().encodeToString(secretPrivateKey.getEncoded()));
			
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	}
    private static void setSecretPublicKey(){
    	try {
    		ClassPathResource cpr=new ClassPathResource("public_key.der");
    		byte [] keyBytes=FileCopyUtils.copyToByteArray(cpr.getInputStream());
//			File pubkFiles=new File(JwtProperties.class.getClassLoader().getResource("public_key.der").getPath());
//			byte[] keyBytes = Files.readAllBytes(pubkFiles.toPath());
			X509EncodedKeySpec spec =
				      new X509EncodedKeySpec(keyBytes);
			KeyFactory kf = KeyFactory.getInstance("RSA");
			//System.out.println("PrivateKey Format "+kf.generatePublic(spec).getFormat());
			secretPublicKey= kf.generatePublic(spec);
			//System.out.println(Base64.getEncoder().encodeToString(secretPublicKey.getEncoded()));
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void setRefreshSecret() {
    	try {

    		ClassPathResource cpr=new ClassPathResource("refresh_key");
    		byte [] keyBytes=FileCopyUtils.copyToByteArray(cpr.getInputStream());
//			File refresh=new File(JwtProperties.class.getClassLoader().getResource("refresh_key").getPath());
			refreshSecret =new String(keyBytes);
    	}
    	catch (IOException e) {
			e.printStackTrace();
		}
    }
    

	
	public PrivateKey getSecretPrivateKey() {
		return secretPrivateKey;
	}

	public PublicKey getSecretPublicKey() {
		return secretPublicKey;
	}

	public long getValidityInMs() {
		return validityInMs;
	}

	public long getRefreshValidityInMs() {
		return refreshValidityInMs;
	}
	
	public String getRefreshSecret() {
		return refreshSecret;
	}

	public String getIssuer() {
		return issuer;
	}
	
	

	
}
