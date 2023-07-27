package com.vaadin.TelrosTestCase.view;

import com.vaadin.TelrosTestCase.components.UserPersonalEditor;
import com.vaadin.TelrosTestCase.model.User;
import com.vaadin.TelrosTestCase.repository.UserRepository;
import com.vaadin.TelrosTestCase.service.SecurityService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@Route("me")
@PermitAll
public class MeView extends VerticalLayout {
    private SecurityService securityService;
    private final UserRepository userRepository;
    private final UserPersonalEditor userPersonalEditor;
    private final Grid<User> grid = new Grid<>(User.class, false);

    @Autowired
    MeView(UserRepository userRepository, UserPersonalEditor userEditor, SecurityService securityService) {
        this.userRepository = userRepository;
        this.userPersonalEditor = userEditor;
        this.securityService = securityService;

        H2 logo = new H2("Мои данные");
        logo.addClassName("logo");
        HorizontalLayout header;
        if (securityService.getAuthenticatedUser() != null) {
            Button logout = new Button("Logout", click ->
                    securityService.logout());
            logout.getElement().getStyle().set("margin-left", "1600px");
            header = new HorizontalLayout(logo, logout);
        } else {
            header = new HorizontalLayout(logo);
        }

        add(header, grid, userEditor);


        /**
         Сделано для упорядочения столбцов в отображении
         */
        grid.addColumn(User::getId).setHeader("№").setSortable(true);
        grid.addColumn(User::getLastName).setHeader("Фамилия").setSortable(true);
        grid.addColumn(User::getFirstName).setHeader("Имя").setSortable(true);
        grid.addColumn(User::getPatronymic).setHeader("Отчество").setSortable(true);
        grid.addColumn(User::getBirthDate).setHeader("Дата рождения").setSortable(true);
        grid.addColumn(User::getEmail).setHeader("Почта").setSortable(true);
        grid.addColumn(User::getPhoneNumber).setHeader("Номер телефона").setSortable(true);
        grid.addColumn(User::getImage).setHeader("Фото");


        grid.asSingleSelect().addValueChangeListener(e -> {
            userEditor.editUser(e.getValue());
        });

        userEditor.setChangeHandler(() -> {
            userEditor.setVisible(false);
        });

        showUser("");
    }

    private void showUser(String name) {
        String myAuthentication = securityService.getAuthenticatedUser().getUsername();
        User me = userRepository.findByLogin(myAuthentication)
                .orElseThrow(() -> new UsernameNotFoundException("User " + myAuthentication + " not found"));
        if (name.isEmpty()) {
            grid.setItems(me);
        } else grid.setItems(userRepository.findByName(name));
    }


}
