import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PaizounSharedModule } from 'app/shared/shared.module';
import { CanchaComponent } from './cancha.component';
import { CanchaDetailComponent } from './cancha-detail.component';
import { CanchaUpdateComponent } from './cancha-update.component';
import { CanchaDeleteDialogComponent } from './cancha-delete-dialog.component';
import { canchaRoute } from './cancha.route';

@NgModule({
  imports: [PaizounSharedModule, RouterModule.forChild(canchaRoute)],
  declarations: [CanchaComponent, CanchaDetailComponent, CanchaUpdateComponent, CanchaDeleteDialogComponent],
  entryComponents: [CanchaDeleteDialogComponent]
})
export class PaizounCanchaModule {}
