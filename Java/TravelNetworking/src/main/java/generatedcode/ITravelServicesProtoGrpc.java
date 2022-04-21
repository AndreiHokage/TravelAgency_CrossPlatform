package generatedcode;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.45.1)",
    comments = "Source: TravelNetworking/src/main/java/chat/network/gRPC/travel.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ITravelServicesProtoGrpc {

  private ITravelServicesProtoGrpc() {}

  public static final String SERVICE_NAME = "ITravelServicesProto";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<generatedcode.Employee,
      generatedcode.TravelResponse> getLoginMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Login",
      requestType = generatedcode.Employee.class,
      responseType = generatedcode.TravelResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<generatedcode.Employee,
      generatedcode.TravelResponse> getLoginMethod() {
    io.grpc.MethodDescriptor<generatedcode.Employee, generatedcode.TravelResponse> getLoginMethod;
    if ((getLoginMethod = ITravelServicesProtoGrpc.getLoginMethod) == null) {
      synchronized (ITravelServicesProtoGrpc.class) {
        if ((getLoginMethod = ITravelServicesProtoGrpc.getLoginMethod) == null) {
          ITravelServicesProtoGrpc.getLoginMethod = getLoginMethod =
              io.grpc.MethodDescriptor.<generatedcode.Employee, generatedcode.TravelResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Login"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generatedcode.Employee.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generatedcode.TravelResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ITravelServicesProtoMethodDescriptorSupplier("Login"))
              .build();
        }
      }
    }
    return getLoginMethod;
  }

  private static volatile io.grpc.MethodDescriptor<generatedcode.Employee,
      generatedcode.TravelResponse> getLogoutMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Logout",
      requestType = generatedcode.Employee.class,
      responseType = generatedcode.TravelResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<generatedcode.Employee,
      generatedcode.TravelResponse> getLogoutMethod() {
    io.grpc.MethodDescriptor<generatedcode.Employee, generatedcode.TravelResponse> getLogoutMethod;
    if ((getLogoutMethod = ITravelServicesProtoGrpc.getLogoutMethod) == null) {
      synchronized (ITravelServicesProtoGrpc.class) {
        if ((getLogoutMethod = ITravelServicesProtoGrpc.getLogoutMethod) == null) {
          ITravelServicesProtoGrpc.getLogoutMethod = getLogoutMethod =
              io.grpc.MethodDescriptor.<generatedcode.Employee, generatedcode.TravelResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Logout"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generatedcode.Employee.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generatedcode.TravelResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ITravelServicesProtoMethodDescriptorSupplier("Logout"))
              .build();
        }
      }
    }
    return getLogoutMethod;
  }

  private static volatile io.grpc.MethodDescriptor<generatedcode.FlightDestDepDTO,
      generatedcode.Flight> getFilterFlightsByDestinationAndDepartureMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FilterFlightsByDestinationAndDeparture",
      requestType = generatedcode.FlightDestDepDTO.class,
      responseType = generatedcode.Flight.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<generatedcode.FlightDestDepDTO,
      generatedcode.Flight> getFilterFlightsByDestinationAndDepartureMethod() {
    io.grpc.MethodDescriptor<generatedcode.FlightDestDepDTO, generatedcode.Flight> getFilterFlightsByDestinationAndDepartureMethod;
    if ((getFilterFlightsByDestinationAndDepartureMethod = ITravelServicesProtoGrpc.getFilterFlightsByDestinationAndDepartureMethod) == null) {
      synchronized (ITravelServicesProtoGrpc.class) {
        if ((getFilterFlightsByDestinationAndDepartureMethod = ITravelServicesProtoGrpc.getFilterFlightsByDestinationAndDepartureMethod) == null) {
          ITravelServicesProtoGrpc.getFilterFlightsByDestinationAndDepartureMethod = getFilterFlightsByDestinationAndDepartureMethod =
              io.grpc.MethodDescriptor.<generatedcode.FlightDestDepDTO, generatedcode.Flight>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "FilterFlightsByDestinationAndDeparture"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generatedcode.FlightDestDepDTO.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generatedcode.Flight.getDefaultInstance()))
              .setSchemaDescriptor(new ITravelServicesProtoMethodDescriptorSupplier("FilterFlightsByDestinationAndDeparture"))
              .build();
        }
      }
    }
    return getFilterFlightsByDestinationAndDepartureMethod;
  }

  private static volatile io.grpc.MethodDescriptor<generatedcode.TravelRequest,
      generatedcode.Flight> getFilterFlightByAvailableSeatsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FilterFlightByAvailableSeats",
      requestType = generatedcode.TravelRequest.class,
      responseType = generatedcode.Flight.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<generatedcode.TravelRequest,
      generatedcode.Flight> getFilterFlightByAvailableSeatsMethod() {
    io.grpc.MethodDescriptor<generatedcode.TravelRequest, generatedcode.Flight> getFilterFlightByAvailableSeatsMethod;
    if ((getFilterFlightByAvailableSeatsMethod = ITravelServicesProtoGrpc.getFilterFlightByAvailableSeatsMethod) == null) {
      synchronized (ITravelServicesProtoGrpc.class) {
        if ((getFilterFlightByAvailableSeatsMethod = ITravelServicesProtoGrpc.getFilterFlightByAvailableSeatsMethod) == null) {
          ITravelServicesProtoGrpc.getFilterFlightByAvailableSeatsMethod = getFilterFlightByAvailableSeatsMethod =
              io.grpc.MethodDescriptor.<generatedcode.TravelRequest, generatedcode.Flight>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "FilterFlightByAvailableSeats"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generatedcode.TravelRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generatedcode.Flight.getDefaultInstance()))
              .setSchemaDescriptor(new ITravelServicesProtoMethodDescriptorSupplier("FilterFlightByAvailableSeats"))
              .build();
        }
      }
    }
    return getFilterFlightByAvailableSeatsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<generatedcode.Ticket,
      generatedcode.Ticket> getAddTicketMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AddTicket",
      requestType = generatedcode.Ticket.class,
      responseType = generatedcode.Ticket.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<generatedcode.Ticket,
      generatedcode.Ticket> getAddTicketMethod() {
    io.grpc.MethodDescriptor<generatedcode.Ticket, generatedcode.Ticket> getAddTicketMethod;
    if ((getAddTicketMethod = ITravelServicesProtoGrpc.getAddTicketMethod) == null) {
      synchronized (ITravelServicesProtoGrpc.class) {
        if ((getAddTicketMethod = ITravelServicesProtoGrpc.getAddTicketMethod) == null) {
          ITravelServicesProtoGrpc.getAddTicketMethod = getAddTicketMethod =
              io.grpc.MethodDescriptor.<generatedcode.Ticket, generatedcode.Ticket>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AddTicket"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generatedcode.Ticket.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generatedcode.Ticket.getDefaultInstance()))
              .setSchemaDescriptor(new ITravelServicesProtoMethodDescriptorSupplier("AddTicket"))
              .build();
        }
      }
    }
    return getAddTicketMethod;
  }

  private static volatile io.grpc.MethodDescriptor<generatedcode.Flight,
      generatedcode.TravelResponse> getAddFlightMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AddFlight",
      requestType = generatedcode.Flight.class,
      responseType = generatedcode.TravelResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<generatedcode.Flight,
      generatedcode.TravelResponse> getAddFlightMethod() {
    io.grpc.MethodDescriptor<generatedcode.Flight, generatedcode.TravelResponse> getAddFlightMethod;
    if ((getAddFlightMethod = ITravelServicesProtoGrpc.getAddFlightMethod) == null) {
      synchronized (ITravelServicesProtoGrpc.class) {
        if ((getAddFlightMethod = ITravelServicesProtoGrpc.getAddFlightMethod) == null) {
          ITravelServicesProtoGrpc.getAddFlightMethod = getAddFlightMethod =
              io.grpc.MethodDescriptor.<generatedcode.Flight, generatedcode.TravelResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AddFlight"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generatedcode.Flight.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generatedcode.TravelResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ITravelServicesProtoMethodDescriptorSupplier("AddFlight"))
              .build();
        }
      }
    }
    return getAddFlightMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ITravelServicesProtoStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ITravelServicesProtoStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ITravelServicesProtoStub>() {
        @java.lang.Override
        public ITravelServicesProtoStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ITravelServicesProtoStub(channel, callOptions);
        }
      };
    return ITravelServicesProtoStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ITravelServicesProtoBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ITravelServicesProtoBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ITravelServicesProtoBlockingStub>() {
        @java.lang.Override
        public ITravelServicesProtoBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ITravelServicesProtoBlockingStub(channel, callOptions);
        }
      };
    return ITravelServicesProtoBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ITravelServicesProtoFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ITravelServicesProtoFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ITravelServicesProtoFutureStub>() {
        @java.lang.Override
        public ITravelServicesProtoFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ITravelServicesProtoFutureStub(channel, callOptions);
        }
      };
    return ITravelServicesProtoFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ITravelServicesProtoImplBase implements io.grpc.BindableService {

    /**
     */
    public void login(generatedcode.Employee request,
        io.grpc.stub.StreamObserver<generatedcode.TravelResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getLoginMethod(), responseObserver);
    }

    /**
     */
    public void logout(generatedcode.Employee request,
        io.grpc.stub.StreamObserver<generatedcode.TravelResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getLogoutMethod(), responseObserver);
    }

    /**
     */
    public void filterFlightsByDestinationAndDeparture(generatedcode.FlightDestDepDTO request,
        io.grpc.stub.StreamObserver<generatedcode.Flight> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFilterFlightsByDestinationAndDepartureMethod(), responseObserver);
    }

    /**
     */
    public void filterFlightByAvailableSeats(generatedcode.TravelRequest request,
        io.grpc.stub.StreamObserver<generatedcode.Flight> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFilterFlightByAvailableSeatsMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<generatedcode.Ticket> addTicket(
        io.grpc.stub.StreamObserver<generatedcode.Ticket> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getAddTicketMethod(), responseObserver);
    }

    /**
     */
    public void addFlight(generatedcode.Flight request,
        io.grpc.stub.StreamObserver<generatedcode.TravelResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAddFlightMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getLoginMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                generatedcode.Employee,
                generatedcode.TravelResponse>(
                  this, METHODID_LOGIN)))
          .addMethod(
            getLogoutMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                generatedcode.Employee,
                generatedcode.TravelResponse>(
                  this, METHODID_LOGOUT)))
          .addMethod(
            getFilterFlightsByDestinationAndDepartureMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                generatedcode.FlightDestDepDTO,
                generatedcode.Flight>(
                  this, METHODID_FILTER_FLIGHTS_BY_DESTINATION_AND_DEPARTURE)))
          .addMethod(
            getFilterFlightByAvailableSeatsMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                generatedcode.TravelRequest,
                generatedcode.Flight>(
                  this, METHODID_FILTER_FLIGHT_BY_AVAILABLE_SEATS)))
          .addMethod(
            getAddTicketMethod(),
            io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
              new MethodHandlers<
                generatedcode.Ticket,
                generatedcode.Ticket>(
                  this, METHODID_ADD_TICKET)))
          .addMethod(
            getAddFlightMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                generatedcode.Flight,
                generatedcode.TravelResponse>(
                  this, METHODID_ADD_FLIGHT)))
          .build();
    }
  }

  /**
   */
  public static final class ITravelServicesProtoStub extends io.grpc.stub.AbstractAsyncStub<ITravelServicesProtoStub> {
    private ITravelServicesProtoStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ITravelServicesProtoStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ITravelServicesProtoStub(channel, callOptions);
    }

    /**
     */
    public void login(generatedcode.Employee request,
        io.grpc.stub.StreamObserver<generatedcode.TravelResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getLoginMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void logout(generatedcode.Employee request,
        io.grpc.stub.StreamObserver<generatedcode.TravelResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getLogoutMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void filterFlightsByDestinationAndDeparture(generatedcode.FlightDestDepDTO request,
        io.grpc.stub.StreamObserver<generatedcode.Flight> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getFilterFlightsByDestinationAndDepartureMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void filterFlightByAvailableSeats(generatedcode.TravelRequest request,
        io.grpc.stub.StreamObserver<generatedcode.Flight> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getFilterFlightByAvailableSeatsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<generatedcode.Ticket> addTicket(
        io.grpc.stub.StreamObserver<generatedcode.Ticket> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getAddTicketMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public void addFlight(generatedcode.Flight request,
        io.grpc.stub.StreamObserver<generatedcode.TravelResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAddFlightMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ITravelServicesProtoBlockingStub extends io.grpc.stub.AbstractBlockingStub<ITravelServicesProtoBlockingStub> {
    private ITravelServicesProtoBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ITravelServicesProtoBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ITravelServicesProtoBlockingStub(channel, callOptions);
    }

    /**
     */
    public generatedcode.TravelResponse login(generatedcode.Employee request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getLoginMethod(), getCallOptions(), request);
    }

    /**
     */
    public generatedcode.TravelResponse logout(generatedcode.Employee request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getLogoutMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<generatedcode.Flight> filterFlightsByDestinationAndDeparture(
        generatedcode.FlightDestDepDTO request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getFilterFlightsByDestinationAndDepartureMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<generatedcode.Flight> filterFlightByAvailableSeats(
        generatedcode.TravelRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getFilterFlightByAvailableSeatsMethod(), getCallOptions(), request);
    }

    /**
     */
    public generatedcode.TravelResponse addFlight(generatedcode.Flight request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAddFlightMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ITravelServicesProtoFutureStub extends io.grpc.stub.AbstractFutureStub<ITravelServicesProtoFutureStub> {
    private ITravelServicesProtoFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ITravelServicesProtoFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ITravelServicesProtoFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<generatedcode.TravelResponse> login(
        generatedcode.Employee request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getLoginMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<generatedcode.TravelResponse> logout(
        generatedcode.Employee request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getLogoutMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<generatedcode.TravelResponse> addFlight(
        generatedcode.Flight request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAddFlightMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LOGIN = 0;
  private static final int METHODID_LOGOUT = 1;
  private static final int METHODID_FILTER_FLIGHTS_BY_DESTINATION_AND_DEPARTURE = 2;
  private static final int METHODID_FILTER_FLIGHT_BY_AVAILABLE_SEATS = 3;
  private static final int METHODID_ADD_FLIGHT = 4;
  private static final int METHODID_ADD_TICKET = 5;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ITravelServicesProtoImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ITravelServicesProtoImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_LOGIN:
          serviceImpl.login((generatedcode.Employee) request,
              (io.grpc.stub.StreamObserver<generatedcode.TravelResponse>) responseObserver);
          break;
        case METHODID_LOGOUT:
          serviceImpl.logout((generatedcode.Employee) request,
              (io.grpc.stub.StreamObserver<generatedcode.TravelResponse>) responseObserver);
          break;
        case METHODID_FILTER_FLIGHTS_BY_DESTINATION_AND_DEPARTURE:
          serviceImpl.filterFlightsByDestinationAndDeparture((generatedcode.FlightDestDepDTO) request,
              (io.grpc.stub.StreamObserver<generatedcode.Flight>) responseObserver);
          break;
        case METHODID_FILTER_FLIGHT_BY_AVAILABLE_SEATS:
          serviceImpl.filterFlightByAvailableSeats((generatedcode.TravelRequest) request,
              (io.grpc.stub.StreamObserver<generatedcode.Flight>) responseObserver);
          break;
        case METHODID_ADD_FLIGHT:
          serviceImpl.addFlight((generatedcode.Flight) request,
              (io.grpc.stub.StreamObserver<generatedcode.TravelResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ADD_TICKET:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.addTicket(
              (io.grpc.stub.StreamObserver<generatedcode.Ticket>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class ITravelServicesProtoBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ITravelServicesProtoBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return generatedcode.TravelProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ITravelServicesProto");
    }
  }

  private static final class ITravelServicesProtoFileDescriptorSupplier
      extends ITravelServicesProtoBaseDescriptorSupplier {
    ITravelServicesProtoFileDescriptorSupplier() {}
  }

  private static final class ITravelServicesProtoMethodDescriptorSupplier
      extends ITravelServicesProtoBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ITravelServicesProtoMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ITravelServicesProtoGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ITravelServicesProtoFileDescriptorSupplier())
              .addMethod(getLoginMethod())
              .addMethod(getLogoutMethod())
              .addMethod(getFilterFlightsByDestinationAndDepartureMethod())
              .addMethod(getFilterFlightByAvailableSeatsMethod())
              .addMethod(getAddTicketMethod())
              .addMethod(getAddFlightMethod())
              .build();
        }
      }
    }
    return result;
  }
}
