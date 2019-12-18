import {Component, OnChanges, OnInit} from '@angular/core';
import {ApiService} from "../../core/api.service";
import api from "@fortawesome/fontawesome";
import {Reward} from "../../shared/model/Model";

@Component({
  selector: 'openmeed-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  issues = [];
  repositories = [];
  assignedIssues = [];
  admin = false;

  constructor(private http: ApiService) {
    if (sessionStorage.getItem("roles") === 'ROLE_USER') {
      this.admin = true;
    }
  }


  ngOnInit() {
    this.http.getRepositories().subscribe((repos) => {
      this.repositories = repos;
      console.log(this.repositories)
      this.repositories.reverse();

      this.repositories.forEach(entry => {
        this.http.getIssues(entry.fullName).subscribe((data) => {
          if (data.length > 0) {
            this.issues.concat(data);
          }
          this.http.getIssuesIncentives().subscribe(res => {
            res.forEach(entry => {
              document.getElementById(entry.issueId).innerText = entry.value.concat('pts')
            })
          });
          console.log(this.issues)
        });
      })
    });
  }

  allocateIncentiveToIssue(event) {
    var reward = new Reward();
    reward.value = event.target.value;
    reward.issueId = event.target.dataset.url;
    reward.type = "pts";

    this.http.assignReward(reward).subscribe(result => {
        document.getElementById(event.target.dataset.url).innerText = reward.value.concat(" pts");
      }
    )
  }

}
