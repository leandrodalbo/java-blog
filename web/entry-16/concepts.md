# Entry 16: Networking Basics

## Protocol

A protocol is an agreed format for how data is structured and sent over a network. Two systems know how to understand each other's bytes.

## The OSI model

It splits network communication into seven layers, each one has a single responsibility.

```
7  Application    HTTP, FTP, SMTP
6  Presentation   data formatting, encryption
5  Session        set up, coordinate, end communication
4  Transport      TCP, UDP
3  Network        IP addressing, routing
2  Data link      LLC, MAC
1  Physical       raw bits over the wire
```

### Physical layer

Transmits raw bits over a physical medium: cables, radio, fiber.

### Data link layer

Provides a reliable link between two devices. Splits into two sublayers:

- **LLC** (Logical Link Control): flow control and error checking.
- **MAC** (Media Access Control): identifies devices on the same network.

### Network layer

Gives every device a unique address and routes data between networks.

- IPv4 addresses are 32 bits.
- IPv6 addresses are 128 bits.

### Transport layer

Handles two way communication between applications, end to end.

- **TCP**: reliable, ordered, connection based.
- **UDP**: fast, no delivery guarantee.

### Session layer

Sets up, coordinates, and ends communication sessions between two devices.

### Presentation layer

Formats data into something the application layer understands: encryption, compression, encoding.

### Application layer

The interface between the application and the network. Protocols like HTTP, FTP, and SMTP live here.

## Firewalls

A firewall controls incoming and outgoing traffic, based on a set of rules.

### AWS security groups

On AWS, we set up **security groups**: a firewall attached to an instance, allowing traffic in and out by rule.

```
Internet
   |
   v
security group: web-sg
   allow  443 (https)  from 0.0.0.0/0
   allow  22  (ssh)    from 203.0.113.0/24
   deny   everything else
   |
   v
EC2 instance
```

```hcl
resource "aws_security_group" "web_sg" {
  name        = "web-sg"
  description = "Allow HTTPS and SSH"
  vpc_id      = aws_vpc.main.id

  ingress {
    description = "HTTPS from anywhere"
    from_port   = 443
    to_port     = 443
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    description = "SSH from office"
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["203.0.113.0/24"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}
```

Everything not explicitly allowed by an ingress rule is denied by default.

### NAT

NAT (Network Address Translation) maps addresses from one network to another.

- **Source NAT**: lets hosts inside a private network reach the internet, by rewriting their private address to a public one.

- **Destination NAT**: forwards incoming traffic to a specific host inside a private network.

## Subnets

A subnet splits one network into smaller networks.

An IP address is split into two parts using a mask: the network part and the host part.

A `/24` mask means the first 24 bits identify the subnet, leaving 8 bits for hosts.

```
121.128.0.1     a host on the subnet
121.128.0.0     network address
121.128.0.255   broadcast address
```

The network address marks the subnet itself, and the broadcast address reaches every host on it. Neither one can be assigned to a device, so a `/24` subnet has 254 usable host addresses, not 256.
