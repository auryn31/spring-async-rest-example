from locust import HttpLocust, TaskSet, task

class UserBehavior(TaskSet):

    @task(1)
    def profile(self):
        self.client.get("/cars-locust")

class WebsiteUser(HttpLocust):
    task_set = UserBehavior
    min_wait = 5000
    max_wait = 9000