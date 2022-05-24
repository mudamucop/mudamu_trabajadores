package com.Mudamu.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Passgenerator {
	public static void main(String ...args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
       
        //El String que mandamos al metodo encode es el password que queremos encriptar.
        //System.out.println(bCryptPasswordEncoder.encode("Aa987654321"));
        /*
         * Resultado: $2a$04$ymRYuQSu.jRSPAWBnv/BYuzptzQa51jpZ/b9tutCaoavrPnlyRDHK
         */
    }
}
