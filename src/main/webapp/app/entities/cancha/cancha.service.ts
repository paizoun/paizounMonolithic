import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICancha } from 'app/shared/model/cancha.model';

type EntityResponseType = HttpResponse<ICancha>;
type EntityArrayResponseType = HttpResponse<ICancha[]>;

@Injectable({ providedIn: 'root' })
export class CanchaService {
  public resourceUrl = SERVER_API_URL + 'api/canchas';

  constructor(protected http: HttpClient) {}

  create(cancha: ICancha): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cancha);
    return this.http
      .post<ICancha>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(cancha: ICancha): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cancha);
    return this.http
      .put<ICancha>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICancha>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICancha[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(cancha: ICancha): ICancha {
    const copy: ICancha = Object.assign({}, cancha, {
      fechaCreacion: cancha.fechaCreacion && cancha.fechaCreacion.isValid() ? cancha.fechaCreacion.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaCreacion = res.body.fechaCreacion ? moment(res.body.fechaCreacion) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((cancha: ICancha) => {
        cancha.fechaCreacion = cancha.fechaCreacion ? moment(cancha.fechaCreacion) : undefined;
      });
    }
    return res;
  }
}
