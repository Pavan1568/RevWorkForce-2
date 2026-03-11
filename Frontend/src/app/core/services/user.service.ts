import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = 'http://localhost:8080/api/admin/users';

  constructor(private http: HttpClient) {}

  getAllUsers() {
    return this.http.get<any[]>(this.baseUrl);
  }

  createUser(data: any) {
    return this.http.post(this.baseUrl, data);
  }

  updateUserStatus(userId: number, active: boolean) {
  return this.http.put(
    `http://localhost:8080/api/admin/users/${userId}/status?active=${active}`,
    {}
  );
}

}