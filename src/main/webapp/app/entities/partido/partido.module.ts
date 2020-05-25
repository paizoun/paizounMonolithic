import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PaizounSharedModule } from 'app/shared/shared.module';
import { PartidoComponent } from './partido.component';
import { PartidoDetailComponent } from './partido-detail.component';
import { PartidoUpdateComponent } from './partido-update.component';
import { PartidoDeleteDialogComponent } from './partido-delete-dialog.component';
import { partidoRoute } from './partido.route';

@NgModule({
  imports: [PaizounSharedModule, RouterModule.forChild(partidoRoute)],
  declarations: [PartidoComponent, PartidoDetailComponent, PartidoUpdateComponent, PartidoDeleteDialogComponent],
  entryComponents: [PartidoDeleteDialogComponent]
})
export class PaizounPartidoModule {}
