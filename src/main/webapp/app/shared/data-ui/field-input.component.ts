import { Component, Input, OnInit } from '@angular/core';
import { FieldType } from './field-def';
import { ControlContainer, NgForm } from '@angular/forms';

@Component({
    selector: 'field-input',
    templateUrl: './field-input.component.html',
    viewProviders: [{ provide: ControlContainer, useExisting: NgForm }]
})
export class FieldInputComponent implements OnInit {

    @Input() public form: NgForm;
    @Input() public entityName: string;

    @Input() public field: string;
    @Input() public type: FieldType;
    @Input() public required: boolean;
    @Input() public min: number;
    @Input() public max: number;
    @Input() public minLength: number;
    @Input() public maxLength: number;

    @Input() public options: any[];
    @Input() public optionNameField: string;

    @Input() public object: any;

    constructor() {
    }

    ngOnInit() {

    }

    trackById(option: any): number {
        return option.id;
    }

    getOptionName(option: any): string {
        return option[this.optionNameField];
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
