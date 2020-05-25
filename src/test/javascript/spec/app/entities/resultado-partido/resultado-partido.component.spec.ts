import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PaizounTestModule } from '../../../test.module';
import { ResultadoPartidoComponent } from 'app/entities/resultado-partido/resultado-partido.component';
import { ResultadoPartidoService } from 'app/entities/resultado-partido/resultado-partido.service';
import { ResultadoPartido } from 'app/shared/model/resultado-partido.model';

describe('Component Tests', () => {
  describe('ResultadoPartido Management Component', () => {
    let comp: ResultadoPartidoComponent;
    let fixture: ComponentFixture<ResultadoPartidoComponent>;
    let service: ResultadoPartidoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaizounTestModule],
        declarations: [ResultadoPartidoComponent]
      })
        .overrideTemplate(ResultadoPartidoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ResultadoPartidoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ResultadoPartidoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ResultadoPartido(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.resultadoPartidos && comp.resultadoPartidos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
