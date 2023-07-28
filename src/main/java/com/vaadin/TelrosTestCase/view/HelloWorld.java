package com.vaadin.TelrosTestCase.view;

import com.vaadin.TelrosTestCase.service.SecurityService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@PermitAll
@Route("")
public class HelloWorld extends VerticalLayout {
    private final SecurityService securityService;

    public HelloWorld(SecurityService securityService) {
        this.securityService = securityService;

        H2 logo = new H2("README для тестового задания ТЕЛРОС");
        H3 greetings = new H3("Приветствую Вас в моем первом web-приложении на Vaadin и поздравляю с успешным запуском приложения!!! Огромное спасибо за это тестовое задание. Было очень интересно и познавательно!!");
        logo.addClassName("logo");
        HorizontalLayout header;
        if (securityService.getAuthenticatedUser() != null) {
            Button logout = new Button("Logout", click ->
                    securityService.logout());
            logout.getElement().getStyle().set("margin-left", "1240px");
            header = new HorizontalLayout(logo, greetings, logout);
        } else {
            header = new HorizontalLayout(logo);
        }

        H4 message1 = new H4("Это мое первое знакомство с фреймворком Vaadin, поэтому прошу: не судите слишком строго :)");
        H3 message2 = new H3("По заданию выполнено: ");
        H5 message3 = new H5("1) Реализована возможность аутентификации и авторизации по логину и паролю (admin:admin) или любого пользователя в БД. Также реализовано разграничение доступа по ролям. " +
                "Для ускорения выполнения проекта было проигнорировано кодирование пароля и выбран тип авторизации Basic Auth.");
        H5 message4 = new H5("2) Реализована CRUD модель для работы с пользователями системы. Для проверки функционала вставьте в адресную строку http://localhost:3000/users " +
                "(АХТУНГ!!! Если у пользователя роль \"USER\", доступ к странице будет закрыт и будет ошибка 403). " +
                "На этой странице можно посмотреть список всех юзеров, выборочно отредактировать, удалить или добавить пользователя.");
        H5 message5 = new H5("Для просмотра информации или изменения данных, нажмите на нужную строку в таблице. После этого появятся кнопки и информационные поля.");
        H5 message6 = new H5("3) Частично реализована CRUD модель для работы с картинками пользователя. ");
        H3 failure = new H3("Что не удалось: ");
        H5 message7 = new H5("1) Увы, базовая реализация автоматического редиректа SpringSecurity на нужные страницы после прохождения авторизации, почему-то не завелась. " +
                "Полагаю, у меня не хаватает пока знаний и опыта работы с VaadinWebSecurity для реализации этого функционала.");
        H3 message8 = new H3("Обращаю внимание, что эндпоинты работают в отдельном режиме! Просто введите нужный URL в адресную строку:");
        H5 message9 = new H5("http://localhost:3000/users - для просмотра и редактирования от лица \"ADMIN\"");
        H5 message10 = new H5("http://localhost:3000/me - для просмотра и редактирования от лица \"USER\" и \"ADMIN\"");
        H3 additional = new H3("Выполнено сверх требований: ");
        H5 message11 = new H5("1) Реализовано окно выборки пользователей в основной таблице по части Имени/Фамилии/Отчества с моментальной демонстрацией результата.");
        H5 message12 = new H5("2) Добавлена кнопка Logout.");
        H5 message13 = new H5("3) Добавлен функционал редактирования данных на странице просмотра профиля авторизованного пользователя." +
                " URL: http://localhost:3000/me Данную страницу могут посещать все пользователи с ролями \"USER\" и \"ADMIN\"");
        H3 overall = new H3("Итоги: ");
        H5 message14 = new H5("1) Еще раз спасибо за это ТЗ. Фреймворк безумно интересный, отчасти, даже \"магичексий\" :) Очень хотелось бы вникнуть в работу с Vaadin на " +
                "качественно новом уровне во время сотрудничества.");
        H5 message15 = new H5("2) Эффективность и качество были бы сильно выше, если бы у меня была возможность посоветоваться с наставниками " +
                "и коллегами по поводу возникающих проблем.");
        H2 goodLuck = new H2("Желаю вам добра и удачи :) ");
        setSpacing(true);

        add(header, greetings, message1, message2, message3, message4, message5, message6, failure, message7, message8, message9,
                 message10, additional, message11, message12, message13, overall, message14, message15, goodLuck);
    }
}
