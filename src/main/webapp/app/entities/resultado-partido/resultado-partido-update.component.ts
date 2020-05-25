import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IResultadoPartido, ResultadoPartido } from 'app/shared/model/resultado-partido.model';
import { ResultadoPartidoService } from './resultado-partido.service';
import { IPartido } from 'app/shared/model/partido.model';
import { PartidoService } from 'app/entities/partido/partido.service';

@Component({
  selector: 'jhi-resultado-partido-update',
  templateUrl: './resultado-partido-update.component.html'
})
export class ResultadoPartidoUpdateComponent implements OnInit {
  isSaving = false;
  partidos: IPartido[] = [];

  editForm = this.fb.group({
    id: [],
    golesEqiopoA: [null, [Validators.required]],
    golesEqiopoB: [null, [Validators.required]],
    ganoEquipoA: [],
    ganoEquipoB: [],
    partido: []
  });

  constructor(
    protected resultadoPartidoService: ResultadoPartidoService,
    protected partidoService: PartidoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resultadoPartido }) => {
      this.updateForm(resultadoPartido);

      this.partidoService
        .query({ filter: 'resultadopartido-is-null' })
        .pipe(
          map((res: HttpResponse<IPartido[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IPartido[]) => {
          if (!resultadoPartido.partido || !resultadoPartido.partido.id) {
            this.partidos = resBody;
          } else {
            this.partidoService
              .find(resultadoPartido.partido.id)
              .pipe(
                map((subRes: HttpResponse<IPartido>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IPartido[]) => (this.partidos = concatRes));
          }
        });
    });
  }

  updateForm(resultadoPartido: IResultadoPartido): void {
    this.editForm.patchValue({
      id: resultadoPartido.id,
      golesEqiopoA: resultadoPartido.golesEqiopoA,
      golesEqiopoB: resultadoPartido.golesEqiopoB,
      ganoEquipoA: resultadoPartido.ganoEquipoA,
      ganoEquipoB: resultadoPartido.ganoEquipoB,
      partido: resultadoPartido.partido
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const resultadoPartido = this.createFromForm();
    if (resultadoPartido.id !== undefined) {
      this.subscribeToSaveResponse(this.resultadoPartidoService.update(resultadoPartido));
    } else {
      this.subscribeToSaveResponse(this.resultadoPartidoService.create(resultadoPartido));
    }
  }

  private createFromForm(): IResultadoPartido {
    return {
      ...new ResultadoPartido(),
      id: this.editForm.get(['id'])!.value,
      golesEqiopoA: this.editForm.get(['golesEqiopoA'])!.value,
      golesEqiopoB: this.editForm.get(['golesEqiopoB'])!.value,
      ganoEquipoA: this.editForm.get(['ganoEquipoA'])!.value,
      ganoEquipoB: this.editForm.get(['ganoEquipoB'])!.value,
      partido: this.editForm.get(['partido'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResultadoPartido>>): void {
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

  trackById(index: number, item: IPartido): any {
    return item.id;
  }
}
