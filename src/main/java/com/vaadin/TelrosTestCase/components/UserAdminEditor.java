package com.vaadin.TelrosTestCase.components;

import com.vaadin.TelrosTestCase.model.Role;
import com.vaadin.TelrosTestCase.model.User;
import com.vaadin.TelrosTestCase.repository.UserRepository;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class UserAdminEditor extends VerticalLayout implements KeyNotifier {
    private final UserRepository userRepository;
    private User user;
    private final TextField login = new TextField("Login");
    private final TextField password = new TextField("Password");
    private final TextField lastName = new TextField("Last name");
    private final TextField firstName = new TextField("First name");
    private final TextField patronymic = new TextField("Patronymic");
    private final DatePicker birthDate = new DatePicker("Select birth date");

    private final TextField email = new TextField("Email");
    private final TextField phoneNumber = new TextField("Phone number");

    private final RadioButtonGroup<Role> role = new RadioButtonGroup<>("Role", Role.ROLE_ADMIN, Role.ROLE_USER);

    private final Button save = new Button("Сохранить", VaadinIcon.CHECK.create());
    private final Button cancel = new Button("Отмена");
    private final Button delete = new Button("Удалить", VaadinIcon.TRASH.create());
    private final HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    private final Binder<User> binder = new Binder<>(User.class);
    @Setter
    private ChangeHandler changeHandler;

    @Autowired
    public UserAdminEditor(UserRepository userRepository) {
        this.userRepository = userRepository;
        add(firstName, actions);
        add(login, password, lastName, firstName, patronymic, birthDate, email, phoneNumber, role, actions);
        binder.bindInstanceFields(this);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editUser(user));
        setVisible(false);

    }

    private void delete() {
        userRepository.delete(user);
        changeHandler.onChange();
    }

    private void save() {
        System.out.println(user.toString());
        userRepository.save(user);
        changeHandler.onChange();
    }

    public void editUser(User newUser) {
        if (newUser == null) {
            setVisible(false);
            return;
        }
        if (newUser.getId() != null) {
            this.user = userRepository.findById(newUser.getId()).orElse(newUser);
        } else {
            this.user = newUser;
        }
        binder.setBean(user);
        setVisible(true);
        lastName.focus();
    }

    public interface ChangeHandler {
        void onChange();
    }
}
