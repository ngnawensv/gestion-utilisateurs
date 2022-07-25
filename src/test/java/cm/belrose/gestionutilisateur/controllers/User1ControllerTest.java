package cm.belrose.gestionutilisateur.controllers;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//pour les méthodes HTTP
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//pour JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static
        org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//pour HTTP status
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cm.belrose.gestionutilisateur.entities.Role1;
import cm.belrose.gestionutilisateur.entities.User1;
import cm.belrose.gestionutilisateur.services.RoleService;
import cm.belrose.gestionutilisateur.services.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class User1ControllerTest {

    private static final Logger log = LoggerFactory.getLogger(User1ControllerTest.class);
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private RoleService roleService; // Ce bean est nécessaire car un utilisateur peut avoir plusieurs role

    User1 user1 = new User1(1L, "Dupont", "password", 1);

    @Before
    public void setUp() throws Exception {
        //Initialisation du setup avant chaque test
        Role1 role = new Role1("USER_ROLE");//initialisation du rôle utilisateur
        Set<Role1> roles = new HashSet<>();
        roles.add(role);
        user1.setRoles(roles);
        List<User1> allUsers = Arrays.asList(user1);

        // Mock de la couche de service
        given(userService.getAllUsers()).willReturn(allUsers);
        when(userService.findUserById(any(Long.class))).thenReturn(Optional.ofNullable(user1));
        when(userService.saveOrUpdateUser(any(User1.class))).thenReturn(user1);
        when(roleService.getAllRolesStream()).thenReturn(roles.stream());
    }

    @Test
    public void testFindAllUsers() throws Exception {
        MvcResult result = mockMvc.perform(get("/user1/user1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound()) //statut HTTP de la réponse
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].login", is(user1.getLogin())))
                .andExpect(jsonPath("$[0].password", is(user1.getPassword())))
                .andExpect(jsonPath("$[0].active", is(user1.getActive())))
                .andReturn();

        // ceci est une redondance, car déjà vérifié par: isFound())
        assertEquals("Réponse incorrecte", HttpStatus.FOUND.value(), result.getResponse().getStatus());

        //on s'assure que la méthode de service getAllUsers() a bien été appelée
        verify(userService).getAllUsers();
    }

    @Test
    public void testSaveUser() throws Exception {

        given(userService.findByLogin("Dupont")).willReturn(null);
        //on exécute la requête
        mockMvc.perform(MockMvcRequestBuilders.post("/user1/user1")
                .content(asJsonString(new User1(null, "Dupont", "password", 1)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());

        //on s'assure que la méthode de service saveOrUpdateUser(User1) a bien été appelée
        verify(userService).saveOrUpdateUser(any(User1.class));

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testFindUserByLogin() throws Exception {
        given(userService.findByLogin("Dupont")).willReturn(Optional.ofNullable(user1));
        //on execute la requête
        mockMvc.perform(get("/user1/user1/login/{loginName}", new String("Dupont"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.login", is(user1.getLogin())))
                .andExpect(jsonPath("$.password", is(user1.getPassword())))
                .andExpect(jsonPath("$.active", is(user1.getActive())));

        //Résultat: on s'assure que la méthode de service findByLogin(login) a bien été appelée
        verify(userService).findByLogin(any(String.class));
    }

    @Test
    public void testFindUserByLogin1() throws Exception {
        when(userService.findByLogin("Dupont")).thenReturn(Optional.ofNullable(user1));
        User1 user1TofindByLogin = new User1((long) 1,"Dupont","password",1);
        String jsonContent = objectMapper.writeValueAsString(user1TofindByLogin);
        //on execute la requête
        MvcResult result = mockMvc.perform(get("/user1/user1/login/{loginName}", new String("Dupont"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isFound())
                .andReturn();
        assertEquals("Erreur de sauvegarde", result.getResponse().getStatus(), HttpStatus.FOUND.value());
        log.info("Val 1 : " + result.getResponse().getStatus(), "Val 2" + HttpStatus.FOUND.value());
        verify(userService).findByLogin(any(String.class));
        User1 user1Result = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<User1>() {
        });
        assertNotNull(user1Result);
       assertEquals(user1TofindByLogin.getId(), user1Result.getId());
        assertEquals(user1TofindByLogin.getLogin(), user1Result.getLogin());
        assertEquals(user1TofindByLogin.getPassword(), user1Result.getPassword());
    }

    @Test
    public void testDeleteUser() throws Exception {
        // on exécute le test
        mockMvc.perform(MockMvcRequestBuilders.delete("/user1/user1/{id}", 1l))
                .andExpect(status().isGone());
        // On vérifie que la méthode de service deleteUser(Id) a bien été appelée
        verify(userService).deleteUser(any(Long.class));
    }

    @Test
    public void testUpdateUser() throws Exception {
        //on exécute la requête
        String jsonContent = objectMapper.writeValueAsString(user1);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/user1/user1/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isOk()).andReturn();
        //on s'assure que la méthode de service saveOrUpdateUser(User1) a bien été appelée
        verify(userService).saveOrUpdateUser(any(User1.class));
        User1 user1Result = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<User1>() {
        });
        assertNotNull(user1Result);
        assertEquals(user1.getId(), user1Result.getId());
        assertEquals(user1.getLogin(), user1Result.getLogin());
        assertEquals(user1.getPassword(), user1Result.getPassword());
        assertEquals(user1.getActive(), user1Result.getActive());
    }

}
