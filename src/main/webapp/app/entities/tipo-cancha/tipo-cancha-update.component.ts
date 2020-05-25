import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITipoCancha, TipoCancha } from 'app/shared/model/tipo-cancha.model';
import { TipoCanchaService } from './tipo-cancha.service';

@Component({
  selector: 'jhi-tipo-cancha-update',
  templateUrl: './tipo-cancha-update.component.html'
})
export class TipoCanchaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombreTipo: [null, [Validators.required]],
    cantidadJugadores: [null, [Validators.required]]
  });

  constructor(protected tipoCanchaService: TipoCanchaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoCancha }) => {
      this.updateForm(tipoCancha);
    });
  }

  updateForm(tipoCancha: ITipoCancha): void {
    this.editForm.patchValue({
      id: tipoCancha.id,
      nombreTipo: tipoCancha.nombreTipo,
      cantidadJugadores: tipoCancha.cantidadJugadores
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipoCancha = this.createFromForm();
    if (tipoCancha.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoCanchaService.update(tipoCancha));
    } else {
      this.subscribeToSaveResponse(this.tipoCanchaService.create(tipoCancha));
    }
  }

  private createFromForm(): ITipoCancha {
    return {
      ...new TipoCancha(),
      id: this.editForm.get(['id'])!.value,
      nombreTipo: this.editForm.get(['nombreTipo'])!.value,
      cantidadJugadores: this.editForm.get(['cantidadJugadores'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoCancha>>): void {
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
