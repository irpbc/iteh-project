import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Student e2e test', () => {

    let navBarPage: NavBarPage;
    let studentDialogPage: StudentDialogPage;
    let studentComponentsPage: StudentComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Students', () => {
        navBarPage.goToEntity('student');
        studentComponentsPage = new StudentComponentsPage();
        expect(studentComponentsPage.getTitle()).toMatch(/itehProjectApp.student.home.title/);

    });

    it('should load create Student dialog', () => {
        studentComponentsPage.clickOnCreateButton();
        studentDialogPage = new StudentDialogPage();
        expect(studentDialogPage.getModalTitle()).toMatch(/itehProjectApp.student.home.createOrEditLabel/);
        studentDialogPage.close();
    });

    it('should create and save Students', () => {
        studentComponentsPage.clickOnCreateButton();
        studentDialogPage.setFirstNameInput('firstName');
        expect(studentDialogPage.getFirstNameInput()).toMatch('firstName');
        studentDialogPage.setLastNameInput('lastName');
        expect(studentDialogPage.getLastNameInput()).toMatch('lastName');
        studentDialogPage.setFullNameInput('fullName');
        expect(studentDialogPage.getFullNameInput()).toMatch('fullName');
        studentDialogPage.save();
        expect(studentDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class StudentComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-student div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class StudentDialogPage {
    modalTitle = element(by.css('h4#myStudentLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    firstNameInput = element(by.css('input#field_firstName'));
    lastNameInput = element(by.css('input#field_lastName'));
    fullNameInput = element(by.css('input#field_fullName'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

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

    setFullNameInput = function(fullName) {
        this.fullNameInput.sendKeys(fullName);
    };

    getFullNameInput = function() {
        return this.fullNameInput.getAttribute('value');
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
