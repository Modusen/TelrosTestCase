package com.vaadin.TelrosTestCase.view;

import com.vaadin.TelrosTestCase.components.UserAdminEditor;
import com.vaadin.TelrosTestCase.model.User;
import com.vaadin.TelrosTestCase.repository.UserRepository;
import com.vaadin.TelrosTestCase.service.SecurityService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;


@Route("/users")
@PermitAll
public class UsersAdminView extends VerticalLayout {
    private SecurityService securityService;
    private final UserRepository userRepository;
    private final UserAdminEditor userEditor;
    private final Grid<User> grid = new Grid<>(User.class, false);

    private final TextField filter = new TextField("",
            "Напечатайте часть Фамилии/Имени/Отчества с учетом регистра для фильтрации");
    private final Button addNewBtn = new Button("Добавить нового");
    private final HorizontalLayout toolbar = new HorizontalLayout(filter, addNewBtn);

    @Autowired
    UsersAdminView(UserRepository userRepository, UserAdminEditor userEditor, SecurityService securityService) {
        this.userRepository = userRepository;
        this.userEditor = userEditor;
        this.securityService = securityService;

        H2 logo = new H2("Список всех пользователей");
        logo.addClassName("logo");
        HorizontalLayout header;
        if (securityService.getAuthenticatedUser() != null) {
            Button logout = new Button("Logout", click ->
                    securityService.logout());
            logout.getElement().getStyle().set("margin-left", "1400px");
            header = new HorizontalLayout(logo, logout);
        } else {
            header = new HorizontalLayout(logo);
        }

        add(header, toolbar, grid, userEditor);

        /**
         Чтобы влезла подсказка
         */
        filter.setWidth("650px");

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

        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> showUser(e.getValue()));
        grid.asSingleSelect().addValueChangeListener(e -> {
            userEditor.editUser(e.getValue());
        });
        addNewBtn.addClickListener(e -> userEditor.editUser(new User()));
        userEditor.setChangeHandler(() -> {
            userEditor.setVisible(false);
            showUser(filter.getValue());
        });

        showUser("");
    }

    private void showUser(String name) {
        if (name.isEmpty()) {
            grid.setItems(userRepository.findAll());
        } else grid.setItems(userRepository.findByName(name));
    }


}
