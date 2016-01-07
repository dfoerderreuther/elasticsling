# elasticsling

quick and dirty elastic search integration in apache sling. It works.

## installation

### download and start sling

http://mirror.serversupportforum.de/apache//sling/org.apache.sling.launchpad-8.jar

java -jar ...


### build and deploy all modules with 

mvn clean install -PautoInstallBundle

### add detail page

curl -X MKCOL -u admin:admin http://localhost:8080/apps/foo
curl -X MKCOL -u admin:admin http://localhost:8080/apps/foo/bar
curl -u admin:admin -T src/html.esp http://localhost:8080/apps/foo/bar/html.esp

### start elasticsearch

https://download.elasticsearch.org/elasticsearch/release/org/elasticsearch/distribution/tar/elasticsearch/2.1.1/elasticsearch-2.1.1.tar.gz

tar xzf ... java -jar ...

# urls

### create content

/create?sling:authRequestLogin=1

### search content

/search?search=sling






