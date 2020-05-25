import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PaizounSharedModule } from 'app/shared/shared.module';
import { ResultadoPartidoComponent } from './resultado-partido.component';
import { ResultadoPartidoDetailComponent } from './resultado-partido-detail.component';
import { ResultadoPartidoUpdateComponent } from './resultado-partido-update.component';
import { ResultadoPartidoDeleteDialogComponent } from './resultado-partido-delete-dialog.component';
import { resultadoPartidoRoute } from './resultado-partido.route';

@NgModule({
  imports: [PaizounSharedModule, RouterModule.forChild(resultadoPartidoRoute)],
  declarations: [
    ResultadoPartidoComponent,
    ResultadoPartidoDetailComponent,
    ResultadoPartidoUpdateComponent,
    ResultadoPartidoDeleteDialogComponent
  ],
  entryComponents: [ResultadoPartidoDeleteDialogComponent]
})
export class PaizounResultadoPartidoModule {}
