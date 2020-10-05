package cm.belrose.gestionutilisateur;

import cm.belrose.gestionutilisateur.InitDataBase.InitUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class GestionutilisateurApplication  implements CommandLineRunner {
	//@Autowired
	//private InitUserService initUserService;

	public static void main(String[] args) {
		SpringApplication.run(GestionutilisateurApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		//initUserService.initRole();
		//initUserService.initUser();

	}
}
