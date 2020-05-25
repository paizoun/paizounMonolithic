import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PaizounSharedModule } from 'app/shared/shared.module';
import { EstadoPartidoComponent } from './estado-partido.component';
import { EstadoPartidoDetailComponent } from './estado-partido-detail.component';
import { EstadoPartidoUpdateComponent } from './estado-partido-update.component';
import { EstadoPartidoDeleteDialogComponent } from './estado-partido-delete-dialog.component';
import { estadoPartidoRoute } from './estado-partido.route';

@NgModule({
  imports: [PaizounSharedModule, RouterModule.forChild(estadoPartidoRoute)],
  declarations: [EstadoPartidoComponent, EstadoPartidoDetailComponent, EstadoPartidoUpdateComponent, EstadoPartidoDeleteDialogComponent],
  entryComponents: [EstadoPartidoDeleteDialogComponent]
})
export class PaizounEstadoPartidoModule {}
