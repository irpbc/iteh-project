
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ItehProjectButtonDemoModule } from './buttons/button/buttondemo.module';
import { ItehProjectSplitbuttonDemoModule } from './buttons/splitbutton/splitbuttondemo.module';

import { ItehProjectDialogDemoModule } from './overlay/dialog/dialogdemo.module';
import { ItehProjectConfirmDialogDemoModule } from './overlay/confirmdialog/confirmdialogdemo.module';
import { ItehProjectTooltipDemoModule } from './overlay/tooltip/tooltipdemo.module';

import { ItehProjectInputTextDemoModule } from './inputs/inputtext/inputtextdemo.module';
import { ItehProjectInputTextAreaDemoModule } from './inputs/inputtextarea/inputtextareademo.module';
import { ItehProjectInputGroupDemoModule } from './inputs/inputgroup/inputgroupdemo.module';
import { ItehProjectCalendarDemoModule } from './inputs/calendar/calendardemo.module';
import { ItehProjectCheckboxDemoModule } from './inputs/checkbox/checkboxdemo.module';
import { ItehProjectChipsDemoModule } from './inputs/chips/chipsdemo.module';
import { ItehProjectColorPickerDemoModule } from './inputs/colorpicker/colorpickerdemo.module';
import { ItehProjectInputMaskDemoModule } from './inputs/inputmask/inputmaskdemo.module';
import { ItehProjectInputSwitchDemoModule } from './inputs/inputswitch/inputswitchdemo.module';
import { ItehProjectPasswordIndicatorDemoModule } from './inputs/passwordindicator/passwordindicatordemo.module';
import { ItehProjectAutoCompleteDemoModule } from './inputs/autocomplete/autocompletedemo.module';
import { ItehProjectSliderDemoModule } from './inputs/slider/sliderdemo.module';
import { ItehProjectSpinnerDemoModule } from './inputs/spinner/spinnerdemo.module';
import { ItehProjectRatingDemoModule } from './inputs/rating/ratingdemo.module';
import { ItehProjectSelectDemoModule } from './inputs/select/selectdemo.module';
import { ItehProjectSelectButtonDemoModule } from './inputs/selectbutton/selectbuttondemo.module';
import { ItehProjectListboxDemoModule } from './inputs/listbox/listboxdemo.module';
import { ItehProjectRadioButtonDemoModule } from './inputs/radiobutton/radiobuttondemo.module';
import { ItehProjectToggleButtonDemoModule } from './inputs/togglebutton/togglebuttondemo.module';
import { ItehProjectEditorDemoModule } from './inputs/editor/editordemo.module';

import { ItehProjectGrowlDemoModule } from './messages/growl/growldemo.module';
import { ItehProjectMessagesDemoModule } from './messages/messages/messagesdemo.module';

import { ItehProjectFileUploadDemoModule } from './fileupload/fileupload/fileuploaddemo.module';

import { ItehProjectAccordionDemoModule } from './panel/accordion/accordiondemo.module';
import { ItehProjectPanelDemoModule } from './panel/panel/paneldemo.module';
import { ItehProjectTabViewDemoModule } from './panel/tabview/tabviewdemo.module';
import { ItehProjectFieldsetDemoModule } from './panel/fieldset/fieldsetdemo.module';
import { ItehProjectToolbarDemoModule } from './panel/toolbar/toolbardemo.module';
import { ItehProjectGridDemoModule } from './panel/grid/griddemo.module';

import { ItehProjectDataTableDemoModule } from './data/datatable/datatabledemo.module';
import { ItehProjectDataGridDemoModule } from './data/datagrid/datagriddemo.module';
import { ItehProjectDataListDemoModule } from './data/datalist/datalistdemo.module';
import { ItehProjectDataScrollerDemoModule } from './data/datascroller/datascrollerdemo.module';
import { ItehProjectPickListDemoModule } from './data/picklist/picklistdemo.module';
import { ItehProjectOrderListDemoModule } from './data/orderlist/orderlistdemo.module';
import { ItehProjectTreeDemoModule } from './data/tree/treedemo.module';
import { ItehProjectTreeTableDemoModule } from './data/treetable/treetabledemo.module';
import { ItehProjectPaginatorDemoModule } from './data/paginator/paginatordemo.module';

import { ItehProjectMenuDemoModule } from './menu/menu/menudemo.module';
import { ItehProjectPanelMenuDemoModule } from './menu/panelmenu/panelmenudemo.module';
import { ItehProjectTieredMenuDemoModule } from './menu/tieredmenu/tieredmenudemo.module';
import { ItehProjectBreadcrumbDemoModule } from './menu/breadcrumb/breadcrumbdemo.module';
import { ItehProjectMegaMenuDemoModule } from './menu/megamenu/megamenudemo.module';
import { ItehProjectMenuBarDemoModule } from './menu/menubar/menubardemo.module';
import { ItehProjectSlideMenuDemoModule } from './menu/slidemenu/slidemenudemo.module';
import { ItehProjectTabMenuDemoModule } from './menu/tabmenu/tabmenudemo.module';

@NgModule({
    imports: [

        ItehProjectMenuDemoModule,
        ItehProjectPanelMenuDemoModule,
        ItehProjectTieredMenuDemoModule,
        ItehProjectBreadcrumbDemoModule,
        ItehProjectMegaMenuDemoModule,
        ItehProjectMenuBarDemoModule,
        ItehProjectSlideMenuDemoModule,
        ItehProjectTabMenuDemoModule,

        ItehProjectInputMaskDemoModule,

        ItehProjectButtonDemoModule,
        ItehProjectSplitbuttonDemoModule,

        ItehProjectInputTextDemoModule,
        ItehProjectInputTextAreaDemoModule,
        ItehProjectInputGroupDemoModule,
        ItehProjectCalendarDemoModule,
        ItehProjectChipsDemoModule,
        ItehProjectInputMaskDemoModule,
        ItehProjectInputSwitchDemoModule,
        ItehProjectPasswordIndicatorDemoModule,
        ItehProjectAutoCompleteDemoModule,
        ItehProjectSliderDemoModule,
        ItehProjectSpinnerDemoModule,
        ItehProjectRatingDemoModule,
        ItehProjectSelectDemoModule,
        ItehProjectSelectButtonDemoModule,
        ItehProjectListboxDemoModule,
        ItehProjectRadioButtonDemoModule,
        ItehProjectToggleButtonDemoModule,
        ItehProjectEditorDemoModule,
        ItehProjectColorPickerDemoModule,
        ItehProjectCheckboxDemoModule,

        ItehProjectGrowlDemoModule,
        ItehProjectMessagesDemoModule,

        ItehProjectFileUploadDemoModule,

        ItehProjectAccordionDemoModule,
        ItehProjectPanelDemoModule,
        ItehProjectTabViewDemoModule,
        ItehProjectFieldsetDemoModule,
        ItehProjectToolbarDemoModule,
        ItehProjectGridDemoModule,

        ItehProjectDialogDemoModule,
        ItehProjectConfirmDialogDemoModule,
        ItehProjectTooltipDemoModule,

        ItehProjectDataTableDemoModule,
        ItehProjectDataGridDemoModule,
        ItehProjectDataListDemoModule,
        ItehProjectDataScrollerDemoModule,
        ItehProjectOrderListDemoModule,
        ItehProjectPickListDemoModule,
        ItehProjectTreeDemoModule,
        ItehProjectTreeTableDemoModule,
        ItehProjectPaginatorDemoModule,
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItehProjectprimengModule {}
