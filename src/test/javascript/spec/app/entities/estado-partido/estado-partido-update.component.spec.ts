import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PaizounTestModule } from '../../../test.module';
import { EstadoPartidoUpdateComponent } from 'app/entities/estado-partido/estado-partido-update.component';
import { EstadoPartidoService } from 'app/entities/estado-partido/estado-partido.service';
import { EstadoPartido } from 'app/shared/model/estado-partido.model';

describe('Component Tests', () => {
  describe('EstadoPartido Management Update Component', () => {
    let comp: EstadoPartidoUpdateComponent;
    let fixture: ComponentFixture<EstadoPartidoUpdateComponent>;
    let service: EstadoPartidoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaizounTestModule],
        declarations: [EstadoPartidoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EstadoPartidoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EstadoPartidoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EstadoPartidoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EstadoPartido(123);
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
        const entity = new EstadoPartido();
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
