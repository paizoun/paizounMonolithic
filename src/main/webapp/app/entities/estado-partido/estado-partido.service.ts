import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEstadoPartido } from 'app/shared/model/estado-partido.model';

type EntityResponseType = HttpResponse<IEstadoPartido>;
type EntityArrayResponseType = HttpResponse<IEstadoPartido[]>;

@Injectable({ providedIn: 'root' })
export class EstadoPartidoService {
  public resourceUrl = SERVER_API_URL + 'api/estado-partidos';

  constructor(protected http: HttpClient) {}

  create(estadoPartido: IEstadoPartido): Observable<EntityResponseType> {
    return this.http.post<IEstadoPartido>(this.resourceUrl, estadoPartido, { observe: 'response' });
  }

  update(estadoPartido: IEstadoPartido): Observable<EntityResponseType> {
    return this.http.put<IEstadoPartido>(this.resourceUrl, estadoPartido, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEstadoPartido>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEstadoPartido[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
