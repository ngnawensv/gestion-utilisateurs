/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.belrose.gestionutilisateur.dao;

import cm.belrose.gestionutilisateur.entities.User1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author PC-NGNAWEN
 * Comme j'ai implementer l'interface JpaRepository plus besoin d'implementer les methodes CRUD par consequant pas besoin
 * de la classe UserDaoImpl
 */
@Repository
public interface UserDao extends JpaRepository<User1, Long> {
    //@Query(value = "select u from User1 u where u.login = ?1")
    Optional<User1> findByLogin(String loginParam);

    //	@Query(value = "select user from User1 user where user.login = ?1 and user.password = ?2")
    User1 findByLoginAndPassword(String login, String password);

}
