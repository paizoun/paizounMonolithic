import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PaizounSharedModule } from 'app/shared/shared.module';
import { TipoCanchaComponent } from './tipo-cancha.component';
import { TipoCanchaDetailComponent } from './tipo-cancha-detail.component';
import { TipoCanchaUpdateComponent } from './tipo-cancha-update.component';
import { TipoCanchaDeleteDialogComponent } from './tipo-cancha-delete-dialog.component';
import { tipoCanchaRoute } from './tipo-cancha.route';

@NgModule({
  imports: [PaizounSharedModule, RouterModule.forChild(tipoCanchaRoute)],
  declarations: [TipoCanchaComponent, TipoCanchaDetailComponent, TipoCanchaUpdateComponent, TipoCanchaDeleteDialogComponent],
  entryComponents: [TipoCanchaDeleteDialogComponent]
})
export class PaizounTipoCanchaModule {}
