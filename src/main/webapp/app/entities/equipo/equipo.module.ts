import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PaizounSharedModule } from 'app/shared/shared.module';
import { EquipoComponent } from './equipo.component';
import { EquipoDetailComponent } from './equipo-detail.component';
import { EquipoUpdateComponent } from './equipo-update.component';
import { EquipoDeleteDialogComponent } from './equipo-delete-dialog.component';
import { equipoRoute } from './equipo.route';

@NgModule({
  imports: [PaizounSharedModule, RouterModule.forChild(equipoRoute)],
  declarations: [EquipoComponent, EquipoDetailComponent, EquipoUpdateComponent, EquipoDeleteDialogComponent],
  entryComponents: [EquipoDeleteDialogComponent]
})
export class PaizounEquipoModule {}
