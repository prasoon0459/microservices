# Commands to setup Spinnaker on CentOS using Helm on Minikube

### Important Points
* Run these commands through root user as Minikube driver is none

### Installing kubectl
* https://kubernetes.io/docs/tasks/tools/install-kubectl/
```
cd ~
sudo curl -LO https://storage.googleapis.com/kubernetes-release/release/`curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt`/bin/linux/amd64/kubectl
sudo chmod +x ./kubectl
sudo mv ./kubectl /usr/local/bin/kubectl
kubectl version --client
```
### Installing minikube
* https://kubernetes.io/docs/tasks/tools/install-minikube/
```
sudo curl -Lo minikube https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
sudo chmod +x minikube
sudo mkdir -p /usr/local/bin/
sudo install minikube /usr/local/bin/
```
### Start minikube with driver none and setup dashboard
```
minikube start --driver=none --kubernetes-version v1.17.6
minikube status
minikube dashboard &
kubectl proxy --address='0.0.0.0' --disable-filter=true &
```
### Install helm
* https://helm.sh/docs/intro/install/
```
curl -fsSL -o get_helm.sh https://raw.githubusercontent.com/helm/helm/master/scripts/get-helm-3
chmod 700 get_helm.sh
./get_helm.sh
helm repo add stable https://kubernetes-charts.storage.googleapis.com/
```
### Setting up Spinnaker

* https://github.com/helm/charts/tree/master/stable/spinnaker
```
helm install spin-chart stable/spinnaker
```
* However this might give issues such as Depolyment and Statefulset of minio and redis target app/v1beta2 when it should be app/v1.

* Solution 1:
  - refer to https://github.com/helm/charts/issues/20207
  - pull chart and unpack. Change deployment and stateful set apps/v1beta2 to apps/v1
  - update spinnaker version in values.yml
    ```
    helm pull stable/spinnaker -d ./spin --untar true
    ```
  - Use grep to find locate beta in directory and change it everywhere
    ```
    helm install spin-works . --debug
    ```
  - Same Error might stil arise with spinnaker clouddriver, change spinnaker version in values.yml. 
* Solution 2 (untested):
  - https://kubernetes.io/blog/2019/07/18/api-deprecations-in-1-16/
  ```
  --runtime-config=apps/v1beta1=true,apps/v1beta2=true,extensions/v1beta1/daemonsets=true,extensions/v1beta1/deployments=true,extensions/v1beta1/replicasets=true,extensions/v1beta1/networkpolicies=true,extensions/v1beta1/podsecuritypolicies=true
  ```
*  Usually the setup takes a lot of time, therefore wait and monitor from dashboard.
* If ceratin images fail to get downloaded, do it manually and reapply deployment from dashboard.
* Change ServiceIP of deck to NodePort or use the kubectl proxy.

* `minikube spin-deck --ip to get IP.`

### Error Graph
![Error Graph](https://github.com/aayush-ag21/microservices/blob/master/k8s/ErrorGraph.jpg)

### Documentation links:
1. https://kubernetes.io/docs/home/
2. https://kubectl.docs.kubernetes.io/
3. https://minikube.sigs.k8s.io/docs/
4. https://helm.sh/docs/helm/
5. https://spinnaker.io/
