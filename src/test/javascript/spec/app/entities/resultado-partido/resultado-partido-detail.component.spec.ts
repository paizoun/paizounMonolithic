import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PaizounTestModule } from '../../../test.module';
import { ResultadoPartidoDetailComponent } from 'app/entities/resultado-partido/resultado-partido-detail.component';
import { ResultadoPartido } from 'app/shared/model/resultado-partido.model';

describe('Component Tests', () => {
  describe('ResultadoPartido Management Detail Component', () => {
    let comp: ResultadoPartidoDetailComponent;
    let fixture: ComponentFixture<ResultadoPartidoDetailComponent>;
    const route = ({ data: of({ resultadoPartido: new ResultadoPartido(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaizounTestModule],
        declarations: [ResultadoPartidoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ResultadoPartidoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ResultadoPartidoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load resultadoPartido on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.resultadoPartido).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
