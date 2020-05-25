import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ResultadoPartidoService } from 'app/entities/resultado-partido/resultado-partido.service';
import { IResultadoPartido, ResultadoPartido } from 'app/shared/model/resultado-partido.model';

describe('Service Tests', () => {
  describe('ResultadoPartido Service', () => {
    let injector: TestBed;
    let service: ResultadoPartidoService;
    let httpMock: HttpTestingController;
    let elemDefault: IResultadoPartido;
    let expectedResult: IResultadoPartido | IResultadoPartido[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ResultadoPartidoService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new ResultadoPartido(0, 0, 0, false, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ResultadoPartido', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ResultadoPartido()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ResultadoPartido', () => {
        const returnedFromService = Object.assign(
          {
            golesEqiopoA: 1,
            golesEqiopoB: 1,
            ganoEquipoA: true,
            ganoEquipoB: true
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ResultadoPartido', () => {
        const returnedFromService = Object.assign(
          {
            golesEqiopoA: 1,
            golesEqiopoB: 1,
            ganoEquipoA: true,
            ganoEquipoB: true
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ResultadoPartido', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
