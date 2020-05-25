import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PaizounTestModule } from '../../../test.module';
import { ResultadoPartidoUpdateComponent } from 'app/entities/resultado-partido/resultado-partido-update.component';
import { ResultadoPartidoService } from 'app/entities/resultado-partido/resultado-partido.service';
import { ResultadoPartido } from 'app/shared/model/resultado-partido.model';

describe('Component Tests', () => {
  describe('ResultadoPartido Management Update Component', () => {
    let comp: ResultadoPartidoUpdateComponent;
    let fixture: ComponentFixture<ResultadoPartidoUpdateComponent>;
    let service: ResultadoPartidoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaizounTestModule],
        declarations: [ResultadoPartidoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ResultadoPartidoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ResultadoPartidoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ResultadoPartidoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ResultadoPartido(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ResultadoPartido();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
