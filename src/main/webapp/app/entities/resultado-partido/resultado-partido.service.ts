import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IResultadoPartido } from 'app/shared/model/resultado-partido.model';

type EntityResponseType = HttpResponse<IResultadoPartido>;
type EntityArrayResponseType = HttpResponse<IResultadoPartido[]>;

@Injectable({ providedIn: 'root' })
export class ResultadoPartidoService {
  public resourceUrl = SERVER_API_URL + 'api/resultado-partidos';

  constructor(protected http: HttpClient) {}

  create(resultadoPartido: IResultadoPartido): Observable<EntityResponseType> {
    return this.http.post<IResultadoPartido>(this.resourceUrl, resultadoPartido, { observe: 'response' });
  }

  update(resultadoPartido: IResultadoPartido): Observable<EntityResponseType> {
    return this.http.put<IResultadoPartido>(this.resourceUrl, resultadoPartido, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IResultadoPartido>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IResultadoPartido[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
