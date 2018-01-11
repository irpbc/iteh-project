import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Lecturer e2e test', () => {

    let navBarPage: NavBarPage;
    let lecturerDialogPage: LecturerDialogPage;
    let lecturerComponentsPage: LecturerComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Lecturers', () => {
        navBarPage.goToEntity('lecturer');
        lecturerComponentsPage = new LecturerComponentsPage();
        expect(lecturerComponentsPage.getTitle()).toMatch(/itehProjectApp.lecturer.home.title/);

    });

    it('should load create Lecturer dialog', () => {
        lecturerComponentsPage.clickOnCreateButton();
        lecturerDialogPage = new LecturerDialogPage();
        expect(lecturerDialogPage.getModalTitle()).toMatch(/itehProjectApp.lecturer.home.createOrEditLabel/);
        lecturerDialogPage.close();
    });

    it('should create and save Lecturers', () => {
        lecturerComponentsPage.clickOnCreateButton();
        lecturerDialogPage.setLoginInput('login');
        expect(lecturerDialogPage.getLoginInput()).toMatch('login');
        lecturerDialogPage.setEmailInput('email');
        expect(lecturerDialogPage.getEmailInput()).toMatch('email');
        lecturerDialogPage.setFirstNameInput('firstName');
        expect(lecturerDialogPage.getFirstNameInput()).toMatch('firstName');
        lecturerDialogPage.setLastNameInput('lastName');
        expect(lecturerDialogPage.getLastNameInput()).toMatch('lastName');
        lecturerDialogPage.save();
        expect(lecturerDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class LecturerComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-lecturer div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class LecturerDialogPage {
    modalTitle = element(by.css('h4#myLecturerLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    loginInput = element(by.css('input#field_login'));
    emailInput = element(by.css('input#field_email'));
    firstNameInput = element(by.css('input#field_firstName'));
    lastNameInput = element(by.css('input#field_lastName'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setLoginInput = function(login) {
        this.loginInput.sendKeys(login);
    };

    getLoginInput = function() {
        return this.loginInput.getAttribute('value');
    };

    setEmailInput = function(email) {
        this.emailInput.sendKeys(email);
    };

    getEmailInput = function() {
        return this.emailInput.getAttribute('value');
    };

    setFirstNameInput = function(firstName) {
        this.firstNameInput.sendKeys(firstName);
    };

    getFirstNameInput = function() {
        return this.firstNameInput.getAttribute('value');
    };

    setLastNameInput = function(lastName) {
        this.lastNameInput.sendKeys(lastName);
    };

    getLastNameInput = function() {
        return this.lastNameInput.getAttribute('value');
    };

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
