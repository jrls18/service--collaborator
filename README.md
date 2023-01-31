## Web Service Rest Gestão de Uma Empresa
#### Microservice responsável por gerenciar o cadastro das empresas, com esse cadastro será possível utilizar o sistema de gestão de controle de estoque e pedidos de acordo com sua versão.

## Install Kustomize
https://kubectl.docs.kubernetes.io/installation/kustomize/docker/

## Install Metrics Server
Versão do kubernetes v1.22.5

kubectl apply -f https://github.com/kubernetes-sigs/metrics-server/releases/latest/download/components.yaml

kubectl -n kube-system get pods

kubectl -n kube-system edit deploy metrics-server

containers:
- args:
- --cert-dir=/tmp
- --secure-port=4443
- --kubelet-preferred-address-types=InternalIP,ExternalIP,Hostname
- --kubelet-use-node-status-port
- --metric-resolution=15s
- --authorization-always-allow-paths=/livez,/readyz
- --kubelet-insecure-tls=true
command:
- /metrics-server
- --kubelet-insecure-tls
- --kubelet-preferred-address-types=InternalIP,ExternalIP,Hostname
- --logtostderr

## Install istio

https://istio.io/latest/docs/setup/getting-started/
https://github.com/istio/istio/releases

istioctl install --set profile=demo -y

kubectl apply -k .\k8s\gtw.yaml

## Deploy Kubernetes

docker build -t service--company-img .
docker run -d -p 10020:5000 -t service--company-img .

kubectl get pods --all-namespaces
kubectl apply -k .\k8s\dev\

## Deploy Kubernetes MiniKube
minikube image build -t service--collaborator-img .
minikube image build -t service--company-img .
minikube image load service--collaborator-img

## Configure HOST
Adicionar no host o ip com o nome da host exemplo


C:\Windows\System32\drivers\etc
For example:
102.54.94.97     rhino.acme.com          source server 
38.25.63.10      x.acme.com               client host
192.168.15.94    cloud.local.develop.corporation.com


## Install grafana no clustering

kubectl create namespace monitoramento

kubectl create configmap prometheus-configmap --from-file=.\Prometheus\server\config\prometheus.yml --namespace=monitoramento

kubectl apply -f .\gateway\

## Swagger 

http://localhost:5000/service--collaborator/swagger-ui.html