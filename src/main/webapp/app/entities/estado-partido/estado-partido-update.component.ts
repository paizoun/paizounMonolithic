import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEstadoPartido, EstadoPartido } from 'app/shared/model/estado-partido.model';
import { EstadoPartidoService } from './estado-partido.service';

@Component({
  selector: 'jhi-estado-partido-update',
  templateUrl: './estado-partido-update.component.html'
})
export class EstadoPartidoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    estado: [null, [Validators.required]]
  });

  constructor(protected estadoPartidoService: EstadoPartidoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ estadoPartido }) => {
      this.updateForm(estadoPartido);
    });
  }

  updateForm(estadoPartido: IEstadoPartido): void {
    this.editForm.patchValue({
      id: estadoPartido.id,
      estado: estadoPartido.estado
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const estadoPartido = this.createFromForm();
    if (estadoPartido.id !== undefined) {
      this.subscribeToSaveResponse(this.estadoPartidoService.update(estadoPartido));
    } else {
      this.subscribeToSaveResponse(this.estadoPartidoService.create(estadoPartido));
    }
  }

  private createFromForm(): IEstadoPartido {
    return {
      ...new EstadoPartido(),
      id: this.editForm.get(['id'])!.value,
      estado: this.editForm.get(['estado'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstadoPartido>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
