## Web Service Rest Gestão de Uma Empresa
#### Microservice responsável por gerenciar o cadastro das empresas, com esse cadastro será possível utilizar o sistema de gestão de controle de estoque e pedidos de acordo com sua versão.

Install Kustomize
https://kubectl.docs.kubernetes.io/installation/kustomize/docker/

https://kustomize.io/

Essa funcionou
kubectl -n kube-system get pods
kubectl apply -f https://github.com/kubernetes-sigs/metrics-server/releases/download/v0.4.5/components.yaml
kubectl -n kube-system edit deploy metrics-server

containers:
- args:
- --cert-dir=/tmp
- --secure-port=4443
- --kubelet-preferred-address-types=InternalIP,ExternalIP,Hostname
- --kubelet-use-node-status-port
- --metric-resolution=15s
command:
- /metrics-server
- --kubelet-insecure-tls
- --kubelet-preferred-address-types=InternalIP

https://gist.github.com/NileshGule/8f772cf04ea6ae9c76d3f3e9186165c2


https://istio.io/latest/docs/setup/getting-started/
https://github.com/istio/istio/releases

restart kubectl

https://www.youtube.com/watch?v=0UDG52REs68


https://www.youtube.com/watch?v=7o7e8OAAWyg

docker build -t service--company-img .
docker run -d -p 10020:5000 -t service--company-img .

kubectl get pods --all-namespaces

publicar no kubert
kubectl apply -k .\k8s\dev\
